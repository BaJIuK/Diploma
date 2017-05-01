package com.bajiuk.diplom.image.shapes;

import com.bajiuk.diplom.data.Point;
import com.sun.istack.internal.NotNull;

import java.awt.*;

public class Line implements Shape {

    @NotNull
    private final Point start;
    @NotNull
    private final Point finish;

    public Line(Point start, Point finish) {
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void draw(Graphics2D image) {
        image.drawLine(start.getX(), start.getY(), finish.getX(), finish.getY());
    }
}
