package com.honhai.foxconn.tankcrash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private int interval = 120;
    float x;
    float y;
    GameData gameData = GameData.getInstance();

    private void initial() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            drawSomething();
        }
    }

    private void drawSomething() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.translate(mCanvas.getWidth()/2,mCanvas.getHeight()/2);
            x = gameData.getMyself().getSite()[0];
            y = gameData.getMyself().getSite()[1];

            drawMap();
            drawTank();
            drawBullet();
            drawFog();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mCanvas != null){
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawMap() {
        mCanvas.save();
        //todo draw map
        mCanvas.drawColor(Color.WHITE);
        mCanvas.restore();
    }

    private void drawFog(){
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setAntiAlias(true);

        Path fog = new Path();
        fog.addCircle(0,0,mCanvas.getHeight()/2, Path.Direction.CCW);
        fog.setFillType(Path.FillType.INVERSE_WINDING);
        mCanvas.drawPath(fog,paint);
    }

    private void drawTank(){
        if (gameData.getPlayerAmount() > 0){
            for (Player p : gameData.getPlayers())
                p.getTank().draw(mCanvas,(p.getSite()[0]-x)*interval,(p.getSite()[1]-y)*interval,interval,interval);
        }
    }

    private void drawBullet(){
        ArrayList<Bullet> b = gameData.getBullet();
        if (b.size() != 0){
            b.forEach(bullet -> bullet.draw(mCanvas,(bullet.getX()-x)*interval,(bullet.getY()-y)*interval,interval,interval));
        }
    }
}
