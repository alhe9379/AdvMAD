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
    GameSurfaceView gameSurfaceView;
    MenuThread menuThread;
    private GameThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurfaceView = findViewById(R.id.surfaceView);
        setContentView(R.layout.activity_game);
        scoreTextView = findViewById(R.id.scoreTextView);

        //https://stackoverflow.com/questions/2335813/how-to-inflate-one-view-with-a-layout
        //https://stackoverflow.com/questions/19118943/how-to-inflate-android-view-in-linearlayout-class
        //gameSurfaceView.inflate();

        //thread = new GameThread(, gameSurfaceView);

        //https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
        //menuThread = new MenuThread(this);
        //runOnUiThread(menuThread);
        Log.d("onCreate", "hi");
    }

    public void updateMenu(){
        //Log.i("UpdateMenu", "in here");
        //scoreTextView.setText(gameSurfaceView.score);
    }

    public void restartLevel(){
//        //https://stackoverflow.com/questions/1397361/how-to-restart-activity-in-android
//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);
    }
}