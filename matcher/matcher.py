import sys
import cv2 as cv
import matplotlib.pyplot as plt
import random
import numpy as np
from scipy.spatial import ConvexHull

MIN_DIST_PX = 50
ITER_COUNT = 1000

def load():
    img1 = cv.imread(sys.argv[1], 0)  # first image
    img2 = cv.imread(sys.argv[2], 0)  # second image
    return img1, img2


def compute(algo, img1, img2):
    kp1, des1 = algo.detectAndCompute(img1, None)
    kp2, des2 = algo.detectAndCompute(img2, None)
    return kp1, kp2, des1, des2


def setupAlgo():
    return setupAlgoSurf()


def setupMatcher():
    return setupMatcherFlann()


# Key point extractors
def setupAlgoSurf():
    return cv.xfeatures2d.SURF_create(5000)


def setupAlgoSift():
    return cv.xfeatures2d.SIFT_create(5000)


# Descriptors matchers
def setupMatcherFlann():
    FLANN_INDEX_KDTREE = 0
    search_params = dict(checks=50)
    index_params = dict(algorithm=FLANN_INDEX_KDTREE, trees=5)
    return cv.FlannBasedMatcher(index_params, search_params)


def setupBFMatcher():
    return cv.BFMatcher()


# Checks if match is good
def isMatchGood(best):
    return best[0].distance < (0.75 * best[1].distance)


def isPointGood(height1, height2, point1, point2):
    part1 = 0.5
    part2 = 0.5
    return (height1 * part1 < point1.pt[1]) & (height2 * part2 > point2.pt[1])


def getGoodMatches(matches):
    good = []
    for match in matches:
        if isMatchGood(match):
            good.append(match[0])
    return good


def makePoints(height1, height2, kp1, kp2, matches):
    filteredMatches = []
    for match in matches:
        point1 = kp1[match.queryIdx]
        point2 = kp2[match.trainIdx]
        if isPointGood(height1, height2, point1, point2):
            filteredMatches.append(match)
    return filteredMatches


def drawPreview(img1, img2, kp1, kp2, good):
    img3 = cv.drawMatches(img1, kp1, img2, kp2, good, outImg=img2)
    plt.imshow(img3), plt.show()


def getImageHeight(image):
    height, width = image.shape
    return height


def getPoints(kp1, kp2, matches):
    src = []
    dst = []
    for match in matches:
        src.append(kp1[match.queryIdx].pt)
        dst.append(kp2[match.trainIdx].pt)
    return src, dst


def savePoints(src, dst):
    output = open(sys.argv[3], 'w')
    for i in range(len(src)):
        output.write(str(src[i][0]) + " " + str(src[i][1]) + " " + str(dst[i][0]) + " " + str(dst[i][1]) + "\n")
    output.close()


def getFourPoints(src, dst):
    numbers = random.sample(range(len(src)), 4)
    srcPoints = []
    dstPoints = []
    for number in numbers:
        srcPoints.append(src[number])
        dstPoints.append(dst[number])
    return np.array(srcPoints), np.array(dstPoints)

def checkDistance(points):
    result = True
    for i in range(len(points)):
        for j in range(i + 1, len(points)):
            result &= cv.norm(points[0], points[1]) > MIN_DIST_PX
    return result

def checkConvexHull(points):
    hull = ConvexHull(points)
    return len(hull.vertices) == len(points)

def getNumberOfGoodPoints(dst, transformedDst):
    okNum = 0
    value = 0.0
    for i in range(len(dst)):
        norm = cv.norm(np.array(dst[i]), np.array(transformedDst[i]))
        if  norm < MIN_DIST_PX:
            okNum += 1
            value += norm
    return okNum, value

def getOnlyGoodPoints(src, dst, homography):
    transformedDst = cv.perspectiveTransform(np.array([src]), homography)[0]
    newSrc = []
    newDst = []
    for i in range(len(dst)):
        norm = cv.norm(np.array(dst[i]), np.array(transformedDst[i]))
        if  norm < MIN_DIST_PX:
            newSrc.append(src[i])
            newDst.append(dst[i])
    return newSrc, newDst

def mySpecialRANSAC(src, dst):
    bestHomography = 0
    bestNum = 0
    bestPrice = 0
    i = 0
    while i < ITER_COUNT :
        sp, dp = getFourPoints(src, dst)

        # Checking distance & convex
        isOk = checkDistance(sp) & checkConvexHull(sp) & checkDistance(dp) & checkConvexHull(dp)
        if (isOk == False) :
            i += 1
            continue

        homography, status = cv.findHomography(sp, dp)

        transformedDst = cv.perspectiveTransform(np.array([src]), homography)[0]
        okNum, price = getNumberOfGoodPoints(dst, transformedDst)

        if (okNum > bestNum or (okNum == bestNum and price < bestPrice)):
            bestNum = okNum
            bestPrice = price
            bestHomography = homography

    return getOnlyGoodPoints(src, dst, bestHomography)

def drawMatches(img1, img2, src, dst):
    matches = []
    kp1 = []
    kp2 = []
    for i in range(len(src)):
        kp1.append(cv.KeyPoint(src[i][0], src[i][1], _size=2))
        kp2.append(cv.KeyPoint(dst[i][0], dst[i][1], _size=2))
        matches.append(cv.DMatch(i,i,0))

    drawPreview(img1, img2, kp1, kp2, matches)

def main():
    (img1, img2) = load()
    kp1, kp2, des1, des2 = compute(setupAlgo(), img1, img2)
    height1 = getImageHeight(img1)
    height2 = getImageHeight(img2)
    matches = makePoints(height1, height2, kp1, kp2, getGoodMatches(setupMatcher().knnMatch(des1, des2, 2)))
    src, dst = getPoints(kp1, kp2, matches)
    src, dst = mySpecialRANSAC(np.array(src), np.array(dst))
    # drawMatches(img1, img2, src, dst)
    savePoints(src, dst)

main()
