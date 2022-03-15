package com.example.wreckingballs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BasicBall extends Ball {
    int velocity = 10;

    public BasicBall(Context context) {
        super(context);
//        ball[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bb1);
//        ball[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bb2);
//        ball[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bb3);
//        ball[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bb4);
//        ball[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bb5);
    }

    @Override
    public Bitmap getBitmap(){
        return ball[ballFrame];
    }

    @Override
    public int getWidth(){
        return ball[0].getWidth();
    }

    @Override
    public int getHeight() {
        return ball[0].getHeight();
    }

}
