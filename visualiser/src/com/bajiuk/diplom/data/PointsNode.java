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
    private List<List<PointPair>> homographies = new ArrayList<>();

    public PointsNode(@NotNull MatchedNode matchedNode) {
        this.matchedNode = matchedNode;
        load();
    }

    private void load() {
        try {
            Scanner input = new Scanner(new FileInputStream(matchedNode.getPointsFile()));
            int all = input.nextInt();
            for (int i = 0; i < all; i++) {
                int pts = input.nextInt();input.nextLine();
                List<PointPair> pairs = new ArrayList<>();
                for (int j = 0; j < pts; j++) {
                    double x1 = Double.valueOf(input.next());
                    double y1 = Double.valueOf(input.next());
                    double x2 = Double.valueOf(input.next());
                    double y2 = Double.valueOf(input.next());
                    pairs.add(new PointPair(new Point(x1, y1), new Point(x2, y2)));
                }
                homographies.add(pairs);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public List<List<PointPair>> getHomographies() {
        return homographies;
    }

    public MatchedNode getMatchedNode() {
        return matchedNode;
    }
}
