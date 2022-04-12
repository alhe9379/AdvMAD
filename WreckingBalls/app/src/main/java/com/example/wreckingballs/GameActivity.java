package com.example.wreckingballs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

//https://stackoverflow.com/questions/10931419/android-drawing-on-surfaceview-and-canvas
//https://www.dev2qa.com/android-surfaceview-drawing-example/
//https://www.codeproject.com/Articles/827608/Android-Basic-Game-Loop
//https://www.youtube.com/watch?v=0xyqbRPuczk&ab_channel=PacktVideo
//http://www.edu4java.com/en/androidgame/androidgame3.html
public class GameActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    Thread gameThread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView view = new SurfaceView(this);
        setContentView(view);
        view.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //gameThread = new Thread();
        //gameThread.
        tryDrawing(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        tryDrawing(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    private void tryDrawing(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
        } else {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void update() {

    }

    private void drawMyStuff(final Canvas canvas) {
        Random random = new Random();
        canvas.drawRGB(255, 128, 128);
    }
}
