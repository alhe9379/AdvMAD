package com.example.wreckingballs;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private int FPS = 30;
    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private GameSurfaceView gameSurfaceView;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameSurfaceView gameSurfaceView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameSurfaceView = gameSurfaceView;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;
        //gameSurfaceView.setBackgroundColor(0Xffffffff);

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            synchronized(surfaceHolder) { this.gameSurfaceView.tryDrawing(surfaceHolder); }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                //this.sleep(waitTime);
                sleep(waitTime);
            } catch (Exception e){
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == FPS) {
                avgFPS = 1000/((totalTime/ frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //System.out.println(avgFPS);
                Log.i("FPS", String.valueOf(avgFPS));
            }
        }
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }
}
