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
        makeRects(getSrcFileWithName("3.jpg"), getSrcFileWithName("4.jpg"));
        makeRects(getSrcFileWithName("4.jpg"), getSrcFileWithName("5.jpg"));
        makeRects(getSrcFileWithName("Photo1.jpg"), getSrcFileWithName("Photo2.jpg"));
        makeRects(getSrcFileWithName("Photo2.jpg"), getSrcFileWithName("Photo3.jpg"));
        makeRects(getSrcFileWithName("Photo3.jpg"), getSrcFileWithName("Photo4.jpg"));
        makeRects(getSrcFileWithName("Photo4.jpg"), getSrcFileWithName("Photo5.jpg"));
        makeRects(getSrcFileWithName("Photo5.jpg"), getSrcFileWithName("Photo6.jpg"));
        makeRects(getSrcFileWithName("Photo6.jpg"), getSrcFileWithName("Photo7.jpg"));
        makeRects(getSrcFileWithName("Photo7.jpg"), getSrcFileWithName("Photo8.jpg"));
        makeRects(getSrcFileWithName("shot1.jpg"), getSrcFileWithName("shot2.jpg"));
        makeRects(getSrcFileWithName("shot2.jpg"), getSrcFileWithName("shot3.jpg"));
        makeRects(getSrcFileWithName("shot3.jpg"), getSrcFileWithName("shot4.jpg"));

        makeRects(getSrcFileWithName("my1.jpg"), getSrcFileWithName("my2.jpg"));
        makeRects(getSrcFileWithName("my2.jpg"), getSrcFileWithName("my3.jpg"));

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
