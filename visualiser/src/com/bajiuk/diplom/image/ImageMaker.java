package com.bajiuk.diplom.image;

import com.bajiuk.diplom.Main;
import com.bajiuk.diplom.image.shapes.Shape;
import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImageMaker {
    @NotNull
    private final BufferedImage image;
    @NotNull
    private final String outImageName;

    public ImageMaker(@NotNull final String srcImage, @NotNull final String dstImage) {
        outImageName = getOutImageName(srcImage, dstImage);
        image = getImage(srcImage);
    }

    public void draw(List<Shape> shapes) {
        Graphics2D graphics2D = image.createGraphics();
        for (Shape shape : shapes) {
            shape.draw(graphics2D);
        }
        graphics2D.dispose();
        writeImage();
    }

    @NotNull
    private BufferedImage getImage(@NotNull final String image) {
        try {
            return ImageIO.read(new File(image));
        } catch (Throwable e) {
            throw new RuntimeException("Image loading error!");
        }
    }

    private void writeImage() {
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
