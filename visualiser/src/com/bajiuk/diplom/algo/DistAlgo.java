package com.bajiuk.diplom.algo;

import com.bajiuk.diplom.data.Point;
import com.bajiuk.diplom.data.PointPair;
import com.bajiuk.diplom.data.PointsNode;
import com.bajiuk.diplom.image.ImageMaker;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class DistAlgo {

    @NotNull
    private final PointsNode pointsNode;

    public DistAlgo(PointsNode pointsNode) {
        this.pointsNode = pointsNode;
    }

    public void processAndSave() {

        String src = pointsNode.getMatchedNode().getDataNode().getSrcImage();
        String dst = pointsNode.getMatchedNode().getDataNode().getDstImage();

        ImageMaker maker = new ImageMaker(src, dst);

        for (int i = 0; i < pointsNode.getHomographies().size(); i++) {
            drawMinMaxpoint(maker, pointsNode.getHomographies().get(i));
        }

        maker.writeImage();
    }

    private void drawMinMaxpoint(ImageMaker imageMaker, List<PointPair> points) {
        double sx = 0;
        double sy = 0;
        double fx = 0;
        double fy = 0;
        for (int i = 0; i < points.size(); i++) {
            PointPair a = points.get(i);
            PointPair b = points.get((i + 1) % points.size());
            imageMaker.drawSrcLine(new PointPair(a.getSrc(), b.getSrc()));
            imageMaker.drawDstLine(new PointPair(a.getDst(), b.getDst()));
            sx += a.getSrc().getX();
            sy += a.getSrc().getY();
            fx += a.getDst().getX();
            fy += a.getDst().getY();
        }

        sx /= points.size();
        sy /= points.size();
        fx /= points.size();
        fy /= points.size();

        imageMaker.drawConnection(new Point(sx, sy), new Point(fx, fy));
    }

}
