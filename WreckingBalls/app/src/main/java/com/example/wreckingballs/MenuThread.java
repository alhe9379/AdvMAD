package com.example.wreckingballs;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MenuThread extends Thread {
    MainActivity mainActivity;

    public MenuThread(MainActivity activity) {
        super();
        Log.i("MenuThread", "In menu thread constructor");
        mainActivity = activity;
    }

    @Override
    public void run(){
        Log.i("MenuThread", "before while(running)");
        //Log.i("MenuThread", "in while(running)");
        mainActivity.updateMenu();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace(); }
    }
}
