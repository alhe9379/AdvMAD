package com.example.wreckingballs;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView scoreTextView;
    MenuThread menuThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.scoreTextView);

        //https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
        menuThread = new MenuThread(this);
        runOnUiThread(menuThread);
        Log.d("onCreate", "hi");
    }

    public void updateMenu(){
        //Log.i("UpdateMenu", "in here");
        //scoreTextView.setText(gameSurfaceView.score);
    }
}