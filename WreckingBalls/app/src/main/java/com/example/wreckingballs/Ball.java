package com.example.wreckingballs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;

public abstract class Ball {
    Bitmap ball[] = new Bitmap[5];
    int ballX, ballY, radius, velocity, ballFrame;
    float mass = (float)(Math.PI * Math.pow(radius, 2));
    float momentum = mass * velocity;
    Point collisionBox[];
    Random random;


    public Ball(Context context) {
        random = new Random();
        ballFrame = 0;
        ballX = (int)(GameView.deviceWidth * .5f);
        ballY = (int)(GameView.deviceHeight*.75f);
    }

    public Bitmap getBitmap(){
        return ball[ballFrame];
    }

    public int getWidth(){
        return ball[0].getWidth();
    }

    public int getHeight(){
        return ball[0].getHeight();
    }
}
