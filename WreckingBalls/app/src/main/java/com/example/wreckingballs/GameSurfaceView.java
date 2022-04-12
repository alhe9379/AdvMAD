package com.example.wreckingballs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Tools tools;
    static int deviceWidth, deviceHeight;
    final long UPDATE_MILLIS = 30;
    //ArrayList<BasicBall> basicBall;
    ArrayList<Block> blocks;
    //ArrayList<BlockPixel> blockPixels;
    //HashMap< Pair<Integer, Integer>, BlockPixel> blockPixels;
    HashMap< Integer, BlockPixel> blockPixels;
    Bitmap ball;
    float ballX, ballY;
    float prevX, prevY;
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
    Paint backgroundPaint;
    Bitmap basicBallShrink[] = new Bitmap[7];
    int ballShrinkI;

    //https://stackoverflow.com/questions/4013725/converting-a-canvas-into-bitmap-image-in-android
    Rect screenRect;
    Bitmap stagingBitmap;
    Canvas stagingCanvas;
    Bitmap ballStagingBitmap;
    Canvas ballStagingCanvas;
    Bitmap blockStagingBitmap;
    Canvas blockStagingCanvas;

    private GameThread thread;


    public GameSurfaceView(Context context) {
        super(context);
        Log.i("Constructor", "In GameSurfaceView constructor");

//        //https://stackoverflow.com/questions/5663671/creating-an-empty-bitmap-and-drawing-though-canvas-in-android
//        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
//        stagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
//        stagingCanvas = new Canvas(stagingBitmap);

        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;
        tools = new Tools();

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(8);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);

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
        //blockPixels = new HashMap<Pair<Integer, Integer>, BlockPixel>();
        blockPixels = new HashMap<Integer, BlockPixel>();

        Block block1 = new Block(20, 700, 250, 0, "green", greenPaint);
        Block block2 = new Block(320, 900, 250, 1, "red", redPaint);
        Block block3 = new Block(620, 1100, 250, 2, "black", blackPaint);
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
                    int x = pI + curBlock.x;
                    int y = pJ + curBlock.y;
                    BlockPixel blockPixel = new BlockPixel(x, y, bI, false, curBlock.armor);
                    //blockPixels.put( new Pair(x, y), blockPixel);
                    blockPixels.put(Tools.xyToInt(x, y, deviceWidth, deviceHeight), blockPixel);
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



        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        //https://stackoverflow.com/questions/5663671/creating-an-empty-bitmap-and-drawing-though-canvas-in-android
        screenRect = new Rect(0,0,this.getWidth(),this.getHeight());
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        stagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
        ballStagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
        blockStagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
        ballStagingCanvas = new Canvas(ballStagingBitmap);
        stagingCanvas = new Canvas(stagingBitmap);
        blockStagingCanvas = new Canvas(blockStagingBitmap);

        initializeStagingBitmap();

        thread.setRunning(true);
        thread.start();

        //tryDrawing(holder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        //tryDrawing(holder);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initializeStagingBitmap(){

        stagingCanvas.drawRGB(255,255,255);
//        blockPixels.forEach( (pair, blockPixel) ->
//                blockStagingCanvas.drawPoint(pair.first, pair.second, blocks.get(blockPixel.blockId).paint ));

        blockPixels.forEach( (integer, blockPixel) ->
                blockStagingCanvas.drawPoint(blockPixel.x, blockPixel.y, blocks.get(blockPixel.blockId).paint ));
    }


    public void tryDrawing(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
        } else {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    //http://android-coding.blogspot.com/2012/01/flickering-problems-due-to-double.html
    private void drawMyStuff(final Canvas canvas) {

        //https://stackoverflow.com/questions/5729377/android-canvas-how-do-i-clear-delete-contents-of-a-canvas-bitmaps-livin
        ballStagingCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        ballStagingCanvas.drawBitmap(basicBallShrink[ballShrinkI], ballX - ball.getWidth()/2, ballY - ball.getHeight()/2, null);

        if(ballIsFlying) {
            for (int x = (int) (ballX - 0.5 * ball.getWidth()); x < ballX + 0.5 * ball.getWidth(); x++) {
                for (int y = (int) (ballY - 0.5 * ball.getHeight()); y < ballY + 0.5 * ball.getHeight(); y++) {
                    //Log.i("BEFORE DESTROY", "yup");
                    //(x - center_x)² + (y - center_y)² < radius²
                    //https://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle
                    if ((Math.pow((x - ballX), 2) + Math.pow((y - ballY), 2)) < Math.pow(.5 * ball.getWidth(), 2)) {
                        BlockPixel bp = blockPixels.get(Tools.xyToInt(x, y, deviceWidth, deviceHeight));
                        //BlockPixel bp = blockPixels.get(new Pair(x,y));

                        if (bp != null) {
                            //Log.i("bp", String.valueOf(bp.isDestroyed));
                            if (!bp.isDestroyed) {
                                //Log.i("WILL DESTROY", "yup");
                                bp.isDestroyed = true;
                                ballVelocity -= bp.armor;
                            }
                        }
                    }
                }
            }
        }

        if(ballIsFlying) {
            prevX = ballX;
            prevY = ballY;
            blockStagingCanvas.drawCircle(prevX, prevY, ball.getWidth()/2, backgroundPaint);

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

        if(!ballIsFlying){ ballStagingCanvas.drawLine(ballX, ballY, downX, downY, linePaint); }

        //https://stackoverflow.com/questions/5729377/android-canvas-how-do-i-clear-delete-contents-of-a-canvas-bitmaps-livin
        //stagingCanvas.drawBitmap(basicBallShrink[ballShrinkI], ballX - ball.getWidth()/2, ballY - ball.getHeight()/2, null);

        stagingCanvas.drawColor(Color.WHITE);
        stagingCanvas.drawBitmap(blockStagingBitmap, null, screenRect, null); //needs to be optimized (don't need to redraw ALL BLOCK PIXELS each time)
        stagingCanvas.drawBitmap(ballStagingBitmap, null, screenRect, null);
        canvas.drawBitmap(stagingBitmap, null, screenRect, null);
    }

    public void update() {
        Log.i("UPDATE", "in update");
    }
}