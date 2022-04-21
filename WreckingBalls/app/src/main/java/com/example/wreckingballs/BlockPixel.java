package com.example.wreckingballs;

//Think of way to allow BlockPixels to move, so blocks can "break apart" or "crack", or fall of the screen, etc...
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
