package com.example.wreckingballs;

public class BlockPixel {
    boolean isDestroyed;
    int x, y, blockId;
    float armor;

    public BlockPixel(int ix, int iy, int iblockId, boolean iisDestroyed, float iarmor) {
        x = ix;
        y = iy;
        blockId = iblockId;
        isDestroyed = iisDestroyed;
        armor = iarmor;
    }

}
