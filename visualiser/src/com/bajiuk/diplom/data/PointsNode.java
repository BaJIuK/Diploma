package com.bajiuk.diplom.data;

import com.sun.istack.internal.NotNull;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PointsNode {
    @NotNull
    private MatchedNode matchedNode;
    @NotNull
    private List<Point> srcPoints = new ArrayList<>();
    @NotNull
    private List<Point> dstPoints = new ArrayList<>();

    public PointsNode(@NotNull MatchedNode matchedNode) {
        this.matchedNode = matchedNode;
        load();
    }

    private void load() {
        try {
            Scanner input = new Scanner(new FileInputStream(matchedNode.getPointsFile()));
            while (input.hasNext()) {
                double x1 = Double.valueOf(input.next());
                double y1 = Double.valueOf(input.next());
                double x2 = Double.valueOf(input.next());
                double y2 = Double.valueOf(input.next());
                srcPoints.add(new Point(x1, y1));
                dstPoints.add(new Point(x2, y2));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public List<Point> getSrcPoints() {
        return srcPoints;
    }

    public List<Point> getDstPoints() {
        return dstPoints;
    }

    public MatchedNode getMatchedNode() {
        return matchedNode;
    }
}
