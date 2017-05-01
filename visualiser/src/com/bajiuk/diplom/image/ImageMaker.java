package com.bajiuk.diplom.image;

import com.bajiuk.diplom.image.shapes.Shape;
import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImageMaker {
    @NotNull
    private final String srcImage;
    @NotNull
    private final BufferedImage image;

    public ImageMaker(@NotNull final String srcImage) {
        this.srcImage = srcImage;
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
            File outputfile = new File(srcImage + ".png");
            ImageIO.write(image, "png", outputfile);
        } catch (Throwable e) {
            throw new RuntimeException("Cannot save Image!");
        }
    }
}
