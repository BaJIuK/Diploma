package com.bajiuk.diplom.data;

import com.sun.istack.internal.NotNull;

public class PointPair {
    @NotNull
    private final Point src;
    @NotNull
    private final Point dst;

    public PointPair(Point src, Point dst) {
        this.src = src;
        this.dst = dst;
    }

    public Point getSrc() {
        return src;
    }

    public Point getDst() {
        return dst;
    }
}
