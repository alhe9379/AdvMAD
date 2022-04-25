package com.example.wreckingballs;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class LevelSelector {
    float canvasWidth;
    float canvasHeight;
    Paint greenPaint = new Paint();
    Paint redPaint = new Paint();
    Paint yellowPaint = new Paint();
    Paint blackPaint = new Paint();

    public LevelSelector(float width, float height){
        canvasWidth = width;
        canvasHeight = height;
        greenPaint.setColor(Color.GREEN);
        redPaint.setColor(Color.RED);
        yellowPaint.setColor(Color.YELLOW);
        blackPaint.setColor(Color.DKGRAY);
    }

    public ArrayList<Block>  getLevel(int level){
        ArrayList<Block> blocks = new ArrayList<Block>();


        switch(level){
            case 1:
                Block yBlock1 = new Block( (int)(canvasWidth - 0.95 * canvasWidth), (int)(canvasHeight * 0.60), 175, 2, "yellow", yellowPaint);
                Block yBlock2 = new Block( (int) (canvasWidth/2 - 88),              (int)(canvasHeight * 0.50), 175, 2, "yellow", yellowPaint);
                Block gBlock1 = new Block( (int) (canvasWidth/2 - 75),              (int)(canvasHeight * 0.20), 150, 0, "green", greenPaint);
                Block rBlock1 = new Block( (int)(canvasWidth - 0.95 * canvasWidth), (int)(canvasHeight * 0.20), 150, 1, "red", redPaint);
                Block rBlock2 = new Block( (int) (canvasWidth * 0.95 - 150),        (int)(canvasHeight * 0.20), 150, 1, "red", redPaint);
                Block bBlock1 = new Block( (int)(canvasWidth/2 - 100),              (int)(canvasHeight * 0.60), 200, 2, "black", blackPaint);
                blocks.add(yBlock2);
                blocks.add(gBlock1);
                blocks.add(rBlock1);
                blocks.add(rBlock2);
                blocks.add(bBlock1);
                blocks.add(yBlock1);
                break;
            case 2:
                break;
            default:
                break;
        }
        return blocks;
    }

}
