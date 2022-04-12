package com.example.wreckingballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new GameSurfaceView(this));
    }

    public void startGame(View view) {
        //Log.i("ImageButton", "Clicked");
        //Intent intent = new Intent(this, StartGame.class);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}