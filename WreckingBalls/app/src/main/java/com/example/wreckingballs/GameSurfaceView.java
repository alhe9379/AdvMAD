package com.example.wreckingballs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    LevelSelector levelSelector;
    int canvasWidth, canvasHeight;
    ArrayList<Block> blocks;
    HashMap< Integer, BlockPixel> blockPixels;
    Bitmap ball;
    boolean validOriginClick;
    float originBallX, originBallY;
    float ballX, ballY;
    float prevX, prevY;
    float futureX, futureY;
    float downX, downY;
    boolean ballIsFlying;
    float ballVelocity;
    float fX, fY;
    float angleOfFlight;
    float tempX, tempY;
    int score;
    Paint linePaint;
    Paint backgroundPaint;
    Paint textPaint;
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

    public GameSurfaceView(Context context, AttributeSet attrs){
//    public GameSurfaceView(Context context){
        super(context, attrs);
        Log.i("Constructor", "In GameSurfaceView constructor");
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(8);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.LTGRAY);
        textPaint = new Paint();
        textPaint.setColor(Color.rgb(255,153,153));
        textPaint.setTextSize(60);
        textPaint.setFakeBoldText(true);

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.bb30);
        prevX = ballX;
        prevY = ballY;
        ballIsFlying = false;
        score = 0;

        blocks = new ArrayList<Block>();
        blockPixels = new HashMap<Integer, BlockPixel>();

        ballShrinkI = 0;
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

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        Log.i("Constructor", "GameSurfaceView(context, attr, defStyle)");
    }

//    public GameSurfaceView(Context context, AttributeSet attrs){
//        super(context, attrs);
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        //https://stackoverflow.com/questions/5663671/creating-an-empty-bitmap-and-drawing-though-canvas-in-android
        canvasWidth = this.getWidth();
        canvasHeight = this.getHeight();
        levelSelector = new LevelSelector(canvasWidth, canvasHeight);

        validOriginClick = false;
        originBallX = canvasWidth*.50f;
        originBallY = canvasHeight*.82f;
        ballX = originBallX;
        ballY = originBallY;
        downX = originBallX;
        downY = originBallY;

        screenRect = new Rect(0,0,canvasWidth,canvasHeight);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        stagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
        ballStagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
        blockStagingBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), conf);
        ballStagingCanvas = new Canvas(ballStagingBitmap);
        stagingCanvas = new Canvas(stagingBitmap);
        blockStagingCanvas = new Canvas(blockStagingBitmap);

        blocks = levelSelector.getLevel(1);
        Block curBlock;
        for(int bI = 0; bI < blocks.size(); bI++){
            curBlock = blocks.get(bI);
            //Log.i("two", String.valueOf(curBlock.size));

            for(int pI = 0; pI < curBlock.size; pI++){
                for(int pJ = 0; pJ < curBlock.size; pJ++){
                    int x = pI + curBlock.x;
                    int y = pJ + curBlock.y;
                    BlockPixel blockPixel = new BlockPixel(x, y, bI, false, curBlock.armor);
                    blockPixels.put(Tools.xyToInt(x, y, canvasWidth, canvasHeight), blockPixel);
                }
            }
        }

        initializeStagingBitmap();
        thread.setRunning(true);
        thread.start();

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
                //if(event.getX() > canvasWidth*.50f - .5*ball.getWidth() && event.getX() < canvasWidth*.50f + .5*ball.getWidth() && event.getY() > canvasHeight*.75f - .5*ball.getHeight() && event.getY() < canvasHeight*.75f + .5*ball.getHeight()) {
                if((Math.pow((event.getX() - originBallX), 2) + Math.pow((event.getY() - originBallY), 2)) < Math.pow(.5 * ball.getWidth(), 2)){
                    fX = fY = tempX = tempY = 0;
                    ballIsFlying = false;
                    downX = event.getX();
                    downY = event.getY();
                    ballVelocity = 45;
                    validOriginClick = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(validOriginClick) {
                    fX = event.getX();
                    fY = event.getY();
                    ballX = event.getX();
                    ballY = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(validOriginClick) {
                    ballIsFlying = true;
                    fX = event.getX();
                    fY = event.getY();
                    //ballVelocity = Tools.distanceBetweenPoints(downX, fY, downY, fY);
                    ballVelocity += (float) (Math.sqrt((downY - fY) * (downY - fY) + (downX - fX) * (downX - fX)) / 10);
                    Log.i("onup", String.valueOf(ballVelocity));

                    ballX = event.getX();
                    ballY = event.getY();
                    angleOfFlight = (float) Tools.getAngle(0, 0, 2, 0, downX, Tools.phoneYtoCartesianY(downY), fX, Tools.phoneYtoCartesianY(fY));
                }
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initializeStagingBitmap(){

        stagingCanvas.drawRGB(255,255,255);
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

            if(ballVelocity > 80) { ballVelocity = 80; }
            //Clear pixels and set them to destroyed
            blockStagingCanvas.drawCircle(prevX, prevY, ball.getWidth()/2, backgroundPaint);
            //https://stackoverflow.com/questions/17931816/how-to-tell-if-an-x-and-y-coordinate-are-inside-my-button#:~:text=Use%20if%20statement%20with%20method,point%20is%20inside%20rectangle%20r.
            for (int x = (int) (ballX - 0.5 * ball.getWidth()); x < ballX + 0.5 * ball.getWidth(); x++) {
                for (int y = (int) (ballY - 0.5 * ball.getHeight()); y < ballY + 0.5 * ball.getHeight(); y++) {
                    //Log.i("BEFORE DESTROY", "yup");
                    //(x - center_x)² + (y - center_y)² < radius²
                    //https://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle
                    if((Math.pow((x - ballX), 2) + Math.pow((y - ballY), 2)) < Math.pow(.5 * ball.getWidth(), 2)) {
                        BlockPixel bp = blockPixels.get(Tools.xyToInt(x, y, canvasWidth, canvasHeight));
                        if (bp != null) {
                            if (!bp.isDestroyed) {
                                bp.isDestroyed = true;
                                score++;
                                ballVelocity = ballVelocity * (1/bp.armor);
                                if(ballVelocity > 80) { ballVelocity = 80; }
                            }
                        }
                    }
                }
            }


            prevX = ballX;
            prevY = ballY;

            ballX = fX - tempX;
            ballY = fY - tempY;

            //https://www.youtube.com/watch?v=eXzGPBYQBnk
            tempX += ballVelocity * Math.cos(angleOfFlight);
            tempY += ballVelocity * Math.sin(angleOfFlight);

            //Stop ball from flying off the screen
            futureX = fX - tempX;
            futureY = fY - tempY;

            if(futureX <= 0 || futureX >= canvasWidth){ //what about corners?
                angleOfFlight = (float)Tools.getAngle(0, 0, 2, 0, prevX, Tools.phoneYtoCartesianY(-prevY), ballX, Tools.phoneYtoCartesianY(-ballY));
                tempX += ballVelocity * Math.cos(angleOfFlight); //https://www.youtube.com/watch?v=eXzGPBYQBnk
                tempY += ballVelocity * Math.sin(angleOfFlight);
            }

            if(futureY <= 0 || futureY >= canvasHeight){ //what about corners?
                angleOfFlight = (float)Tools.getAngle(0, 0, 2, 0, -prevX, Tools.phoneYtoCartesianY(prevY), -ballX, Tools.phoneYtoCartesianY(ballY));
                tempX += ballVelocity * Math.cos(angleOfFlight); //https://www.youtube.com/watch?v=eXzGPBYQBnk
                tempY += ballVelocity * Math.sin(angleOfFlight);
            }

            if(ballVelocity > 0){
                //TODO make the slow down follow exponential decay, to look more natural
                //  To do this I will likely need an initialBallVelocity variable
                ballVelocity = (float)(ballVelocity * 0.99);
                //Log.i("ball velocity", String.valueOf(ballVelocity));
            }
            if(ballVelocity  <= 5) {
                ballVelocity = 0;
                ballShrinkI++;
                if (ballShrinkI == basicBallShrink.length) {
                    ballX = originBallX;
                    ballY = originBallY;
                    prevX = ballX;
                    prevY = ballY;
                    tempX = ballX;
                    tempY = ballY;
                    ballShrinkI = 0;
                    ballIsFlying = false;
                    validOriginClick = false;
                }
            }
        }

        if(!ballIsFlying){ ballStagingCanvas.drawLine(ballX, ballY, downX, downY, linePaint); }

        //https://stackoverflow.com/questions/5729377/android-canvas-how-do-i-clear-delete-contents-of-a-canvas-bitmaps-livin
        //stagingCanvas.drawBitmap(basicBallShrink[ballShrinkI], ballX - ball.getWidth()/2, ballY - ball.getHeight()/2, null);

        stagingCanvas.drawColor(Color.LTGRAY);
        stagingCanvas.drawBitmap(blockStagingBitmap, null, screenRect, null); //needs to be optimized (don't need to redraw ALL BLOCK PIXELS each time)
        stagingCanvas.drawBitmap(ballStagingBitmap, null, screenRect, null);
        canvas.drawBitmap(stagingBitmap, null, screenRect, null);

        //https://stackoverflow.com/questions/12166476/android-canvas-drawtext-set-font-size-from-width
        canvas.drawText(String.valueOf(score), 10, 60, textPaint);
    }

    public void update() {
        Log.i("UPDATE", "in update");
    }
}