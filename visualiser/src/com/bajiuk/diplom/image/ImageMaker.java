package com.bajiuk.diplom.image;

import com.bajiuk.diplom.Main;
import com.bajiuk.diplom.data.*;
import com.bajiuk.diplom.data.Point;
import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageMaker {
    @NotNull
    private final BufferedImage image;
    @NotNull
    private Graphics2D graphics2D;
    @NotNull
    private final String outImageName;

    private int dx;

    public ImageMaker(@NotNull final String srcImage, @NotNull final String dstImage) {
        outImageName = getOutImageName(srcImage, dstImage);
        image = getImage(srcImage, dstImage);
    }

    public void drawSrcLine(@NotNull PointPair pointPair) {
        graphics2D.drawLine(pointPair.getSrc().getX(), pointPair.getSrc().getY(),
                pointPair.getDst().getX(), pointPair.getDst().getY());
    }

    public void drawDstLine(@NotNull PointPair pointPair) {
        graphics2D.drawLine(pointPair.getSrc().getX() + dx, pointPair.getSrc().getY(),
                pointPair.getDst().getX() + dx, pointPair.getDst().getY());
    }

    public void drawConnection(@NotNull Point src, Point dst) {
        graphics2D.drawLine(src.getX(), src.getY(),
                dst.getX() + dx, dst.getY());
    }

    @NotNull
    private BufferedImage getImage(@NotNull final String src, @NotNull final String dst) {
        try {
            BufferedImage srcImage = ImageIO.read(new File(src));
            BufferedImage dstImage = ImageIO.read(new File(dst));
            dx = srcImage.getWidth();
            int width = srcImage.getWidth() + dstImage.getWidth();
            int heigth = Math.max(srcImage.getHeight(), dstImage.getHeight());
            BufferedImage outImage = new BufferedImage(width, heigth, srcImage.getType());
            graphics2D = outImage.createGraphics();
            graphics2D.drawImage(srcImage, 0, 0, null);
            graphics2D.drawImage(dstImage, srcImage.getWidth(), 0, null);
            return outImage;
        } catch (Throwable e) {
            throw new RuntimeException("Image loading error!");
        }
    }

    public void writeImage() {
        graphics2D.dispose();
        try {
            File outputfile = new File(outImageName);
            ImageIO.write(image, "png", outputfile);
        } catch (Throwable e) {
            throw new RuntimeException("Cannot save Image!");
        }
    }

    private static String getOutImageName(String src, String dst) {
        return new File(Main.DST_DIR_IMAGES,  new File(src).getName() + "_" + new File(dst).getName() + ".png")
                .getAbsolutePath();
    }
}
