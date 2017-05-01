package com.bajiuk.diplom.data;

import com.sun.istack.internal.NotNull;

public class DataNode {

    @NotNull
    private final String srcImage;
    @NotNull
    private final String dstImage;

    public DataNode(@NotNull final String srcImage, @NotNull final String dstImage) {
        this.srcImage = srcImage;
        this.dstImage = dstImage;
    }

    @NotNull
    public String getSrcImage() {
        return srcImage;
    }

    @NotNull
    public String getDstImage() {
        return dstImage;
    }
}
