package com.bajiuk.diplom;

import com.bajiuk.diplom.algo.DistAlgo;
import com.bajiuk.diplom.data.DataNode;
import com.bajiuk.diplom.data.MatchedNode;
import com.bajiuk.diplom.exec.ScriptRunner;
import com.sun.istack.internal.NotNull;

public class Main {

    public static void main(String[] args) {
        makeRects("/Users/bajiuk/diplom/matcher/input/1.jpg", "/Users/bajiuk/diplom/matcher/input/2.jpg");
        makeRects("/Users/bajiuk/diplom/matcher/input/2.jpg", "/Users/bajiuk/diplom/matcher/input/3.jpg");
        makeRects("/Users/bajiuk/diplom/matcher/input/Photo1.jpg", "/Users/bajiuk/diplom/matcher/input/Photo2.jpg");
    }

    private static void makeRects(@NotNull final String src, @NotNull final String dst) {
        DataNode dataNode = new DataNode(src, dst);
        MatchedNode matchedNode = new MatchedNode(dataNode);
        ScriptRunner scriptRunner = new ScriptRunner(matchedNode);
        DistAlgo distAlgo = new DistAlgo(scriptRunner.execute());
        distAlgo.processAndSave();
    }
}
