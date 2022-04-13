package com.example.wreckingballs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import java.util.ArrayList;

public class Block {
//    ArrayList<BlockPixel> blockPixels;
    int x, y, size, blockId;
    float armor;
    String type;
    Paint paint;

    public Block(int ix, int iy, int isize, int iblockId, String itype, Paint ipaint) {
        x = ix;
        y = iy;
        size = isize;
        blockId = iblockId;
        type = itype;
        paint = ipaint;

//        if(type == "green") { armor = (float)0.002; }
//        if(type == "red"){ armor = (float)0.005; }
//        if(type == "black") { armor = (float).05; }

        if(type == "green") { armor = (float)1.0001; }
        if(type == "red"){ armor = (float)1.0002; }
        if(type == "black") { armor = (float)1.0006; }

    }

//    public int getX(){ return x; }
//    public int getY(){ return y; }
//    public int getSize(){ return size; }
//    public int getBlockId(){ return blockId; }
}
