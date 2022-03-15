package com.example.wreckingballs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    Tools tools;
    Bitmap background;
    Rect rect;
    static int deviceWidth, deviceHeight;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    ArrayList<BasicBall> basicBall;
    ArrayList<Block> blocks;
    ArrayList<BlockPixel> blockPixels;
    Bitmap ball;
    float ballX, ballY;
    float downX, downY;
    boolean ballIsFlying;
    float ballVelocity;
    float fX, fY;
    float dX, dY;
    float angleOfFlight;
    float tempX, tempY;
    Paint linePaint;
    Paint greenPaint;
    Paint redPaint;
    Paint blackPaint;
    Bitmap basicBallShrink[] = new Bitmap[7];
    int ballShrinkI;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;
        rect = new Rect(0, 0, deviceWidth, deviceHeight);
        handler = new Handler();
        runnable = new Runnable(){
            @Override
            public void run() {
                invalidate();
            }
        };

        tools = new Tools();

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(8);

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);

        blackPaint = new Paint();
        blackPaint.setColor(Color.DKGRAY);

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.bb30);
        ballX = deviceWidth*.50f;
        ballY = deviceHeight*.75f;
        ballIsFlying = false;
        ballVelocity = 60;
        downX = deviceWidth*.50f;
        downY = deviceHeight*.75f;
        fX = fY = 0;
        dX = dY = 0;
        tempX = tempY = 0;

        blocks = new ArrayList<Block>();
        blockPixels = new ArrayList<BlockPixel>();

        Block block1 = new Block(20, 700, 150, 0, "green", greenPaint);
        Block block2 = new Block(320, 900, 150, 1, "red", redPaint);
        Block block3 = new Block(620, 1100, 150, 2, "black", blackPaint);
        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);

        ballShrinkI = 0;

        Block curBlock;
        for(int bI = 0; bI < blocks.size(); bI++){
            curBlock = blocks.get(bI);
            //Log.i("two", String.valueOf(curBlock.size));

            for(int pI = 0; pI < curBlock.size; pI++){
                for(int pJ = 0; pJ < curBlock.size; pJ++){
                    BlockPixel blockPixel = new BlockPixel(pI + curBlock.x, pJ + curBlock.y, bI, false, curBlock.armor);
                    blockPixels.add(blockPixel);
                }
            }
        }

        basicBallShrink[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bb30);
        basicBallShrink[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bb25);
        basicBallShrink[2] = BitmapFactory.decodeResource(getResources(), R.drawable.bb20);
        basicBallShrink[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bb15);
        basicBallShrink[4] = BitmapFactory.decodeResource(getResources(), R.drawable.bb10);
        basicBallShrink[5] = BitmapFactory.decodeResource(getResources(), R.drawable.bb5);
        basicBallShrink[6] = BitmapFactory.decodeResource(getResources(), R.drawable.bb1);
        //Log.i("three", String.valueOf(blockPixels.size()));
    }

    // https://www.youtube.com/watch?v=9uoYHqg_z5o&list=PLGY_UftsHsIZtNO0k-mvG_s6-VmWAlc2y&index=22&ab_channel=SandipBhattacharya
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rect, null);

        //Log.i("four", String.valueOf(blockPixels.size()));
        for(int i = 0; i < blockPixels.size(); i++){
            if(!blockPixels.get(i).isDestroyed) {
                canvas.drawPoint(blockPixels.get(i).x, blockPixels.get(i).y, blocks.get( blockPixels.get(i).blockId ).paint);
            }

            //(x - center_x)² + (y - center_y)² < radius²
            //https://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle
            if( !blockPixels.get(i).isDestroyed && (Math.pow((blockPixels.get(i).x - ballX), 2) + Math.pow((blockPixels.get(i).y - ballY), 2)) < Math.pow(.5*ball.getWidth(), 2) ){
                blockPixels.get(i).isDestroyed = true;
                ballVelocity -= blockPixels.get(i).armor;
            }
        }

        if(ballIsFlying) {
            ballX = fX - tempX;
            ballY = fY - tempY;
            tempX += ballVelocity * -Math.cos(angleOfFlight - Math.PI); //https://www.youtube.com/watch?v=eXzGPBYQBnk
            tempY += ballVelocity * Math.sin(angleOfFlight - Math.PI);
            if(ballX - ball.getWidth()/2 < 0 || ballX + ball.getWidth()/2 > deviceWidth || ballY + ball.getHeight()/2 > deviceHeight || ballY - ball.getHeight()/2 < 0){
//                tempX += ballVelocity * -Math.cos((angleOfFlight - Math.PI) - Math.PI + 2*angleOfFlight); //https://www.youtube.com/watch?v=eXzGPBYQBnk
//                tempY += ballVelocity * Math.sin((angleOfFlight - Math.PI) - Math.PI + 2*angleOfFlight);

                tempX += ballVelocity * -Math.cos((angleOfFlight - Math.PI) + Math.PI - angleOfFlight); //https://www.youtube.com/watch?v=eXzGPBYQBnk
                tempY += ballVelocity * Math.sin((angleOfFlight - Math.PI) - Math.PI + angleOfFlight);
            }

            if(ballVelocity > 0){ ballVelocity--; }
            if(ballVelocity  <= 0) {
                ballVelocity = ballVelocity/100;
                ballShrinkI++;
                if (ballShrinkI == basicBallShrink.length) {
                    ballX = deviceWidth*.50f;
                    ballY = deviceHeight*.75f;
                    tempX = tempY = 0;
                    ballShrinkI = 0;
                    ballIsFlying = false;
                }
            }
        }

        if(!ballIsFlying){ canvas.drawLine(ballX, ballY, downX, downY, linePaint); }

        canvas.drawBitmap(basicBallShrink[ballShrinkI], ballX - ball.getWidth()/2, ballY - ball.getHeight()/2, null);

        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() > deviceWidth*.50f - .5*ball.getWidth() && event.getX() < deviceWidth*.50f + .5*ball.getWidth() && event.getY() > deviceHeight*.75f - .5*ball.getHeight() && event.getY() < deviceHeight*.75f + .5*ball.getHeight()) {
                    dX = dY = fX = fY = tempX = tempY = 0;
                    ballIsFlying = false;
                    downX = event.getX();
                    downY = event.getY();
                    ballVelocity = 60;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                fX = event.getX();
                fY = event.getY();
                ballX = event.getX();
                ballY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                ballIsFlying = true;
                fX = event.getX();
                fY = event.getY();
                ballX = event.getX();
                ballY = event.getY();
                angleOfFlight = (float) tools.getAngle(0, 0, 2, 0, downX, downY, fX, fY);
                break;
        }
        return true;
    }
}
