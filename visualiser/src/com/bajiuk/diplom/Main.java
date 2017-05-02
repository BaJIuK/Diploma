package com.bajiuk.diplom;

import com.bajiuk.diplom.algo.DistAlgo;
import com.bajiuk.diplom.data.DataNode;
import com.bajiuk.diplom.data.MatchedNode;
import com.bajiuk.diplom.exec.ScriptRunner;
import com.sun.istack.internal.NotNull;

import java.io.File;

public class Main {

    public static File WORKING_DIR = new File(System.getProperty("user.dir"));
    public static File ROOT_DIR = WORKING_DIR.getParentFile();
    public static File MATCHER_DIR = new File(ROOT_DIR,"matcher");
    public static File SRC_DIR = new File(ROOT_DIR,"src");
    public static File DST_DIR = new File(ROOT_DIR,"dst");
    public static File DST_DIR_IMAGES = new File(DST_DIR,"images");
    public static File DST_DIR_POINTS = new File(DST_DIR,"points");

    public static void main(String[] args) {
        System.out.println(WORKING_DIR);
        makeRects(getSrcFileWithName("1.jpg"), getSrcFileWithName("2.jpg"));
        makeRects(getSrcFileWithName("2.jpg"), getSrcFileWithName("3.jpg"));
        makeRects(getSrcFileWithName("Photo1.jpg"), getSrcFileWithName("Photo2.jpg"));
    }

    private static void makeRects(@NotNull final String src, @NotNull final String dst) {
        DataNode dataNode = new DataNode(src, dst);
        MatchedNode matchedNode = new MatchedNode(dataNode);
        ScriptRunner scriptRunner = new ScriptRunner(matchedNode);
        DistAlgo distAlgo = new DistAlgo(scriptRunner.execute());
        distAlgo.processAndSave();
    }

    private static String getSrcFileWithName(@NotNull final String name) {
        return new File(SRC_DIR, name).getAbsolutePath();
    }


}
