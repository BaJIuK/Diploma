package com.bajiuk.diplom.algo;

import com.bajiuk.diplom.data.Point;
import com.bajiuk.diplom.data.PointsNode;
import com.bajiuk.diplom.image.ImageMaker;
import com.bajiuk.diplom.image.shapes.Line;
import com.bajiuk.diplom.image.shapes.Shape;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DistAlgo {

    @NotNull
    private final PointsNode pointsNode;

    public DistAlgo(PointsNode pointsNode) {
        this.pointsNode = pointsNode;
    }

    public void processAndSave() {
        List<Point> srcPoints = pointsNode.getSrcPoints();
        List<Point> dstPoints = pointsNode.getDstPoints();

        String src = pointsNode.getMatchedNode().getDataNode().getSrcImage();
        String dst = pointsNode.getMatchedNode().getDataNode().getDstImage();

        ImageMaker imageMaker1 = new ImageMaker(src, dst);
        ImageMaker imageMaker2 = new ImageMaker(dst, src);

        drawMinMaxpoint(imageMaker1, srcPoints);
        drawMinMaxpoint(imageMaker2, dstPoints);
    }

    private void drawMinMaxpoint(ImageMaker imageMaker, List<Point> points) {
        double minX = Double.MAX_VALUE;
        double maxX = 0;
        double minY = Double.MAX_VALUE;
        double maxY = 0;

        for (Point point : points) {
            minX = Math.min(point.getX(), minX);
            minY = Math.min(point.getY(), minY);
            maxX = Math.max(point.getX(), maxX);
            maxY = Math.max(point.getY(), maxY);
        }

        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Line(new Point(minX, minY), new Point(minX, maxY)));
        shapes.add(new Line(new Point(minX, maxY), new Point(maxX, maxY)));
        shapes.add(new Line(new Point(maxX, maxY), new Point(maxX, minY)));
        shapes.add(new Line(new Point(maxX, minY), new Point(minX, minY)));
        imageMaker.draw(shapes);
    }

}
