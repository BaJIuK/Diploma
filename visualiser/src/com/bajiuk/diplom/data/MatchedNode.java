package com.bajiuk.diplom.data;

import com.bajiuk.diplom.Main;
import com.sun.istack.internal.NotNull;

import java.io.File;

public class MatchedNode {

    @NotNull
    private final DataNode dataNode;
    @NotNull
    private final String pointsFile;

    public MatchedNode(@NotNull final DataNode dataNode) {
        this.dataNode = dataNode;
        this.pointsFile = makePointsFile(dataNode);
    }

    @NotNull
    public String getPointsFile() {
        return pointsFile;
    }

    public DataNode getDataNode() {
        return dataNode;
    }

    private static String makePointsFile(DataNode dataNode) {
        File src = new File(dataNode.getSrcImage());
        File dst = new File(dataNode.getDstImage());
        File outDir = Main.DST_DIR_POINTS;
        File outFile = new File(outDir,getNameWithoutExt(src) + "_to_" + getNameWithoutExt(dst) + ".txt");
        outDir.mkdirs();
        return outFile.getAbsolutePath();
    }

    private static String getNameWithoutExt(File file) {
        String name = file.getName();
        try {
            return name.substring(0, name.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

}
