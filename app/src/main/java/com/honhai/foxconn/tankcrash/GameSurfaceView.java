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
    Paint fogPaint = new Paint();
    Path fog;
    ArrayList<Bullet> b;

    private void initial() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        fogPaint.setColor(Color.DKGRAY);
        fogPaint.setAntiAlias(true);

        fog = new Path();
        fog.setFillType(Path.FillType.INVERSE_WINDING);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
        fog.addCircle(0,0,getHeight()/2, Path.Direction.CCW);
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
            mCanvas.drawColor(Color.WHITE);
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
        MapData[][] map = gameData.getMap();
        if (map != null)
            onDrawMap(map);
        mCanvas.restore();
    }

    private void onDrawMap(MapData[][] map) {
        Paint one = new Paint();
        one.setColor(Color.CYAN);
        Paint two = new Paint();
        two.setColor(Color.BLUE);
        for (float j = y-6 ; j <= y+6 ; j++){
            for (float i = x-6 ; i <= x+6 ; i++){
                if (j < 0 || i < 0 || j >= map.length || i >= map[0].length)
                    mCanvas.drawCircle((i-x)*interval,(j-y)*interval,30,new Paint());
                else {
                    switch (map[(int) j][(int) i]) {
                        case TEST_ROAD:
                            mCanvas.drawCircle((i-x)*interval,(j-y)*interval, 30, one);
                            break;
                        case TEST_PILLAR:
                            mCanvas.drawCircle((i-x)*interval,(j-y)*interval, 30, two);
                            break;
                    }
                }
            }
        }
    }

    private void drawFog(){
        mCanvas.drawPath(fog, fogPaint);
    }

    private void drawTank(){
        if (gameData.getPlayerAmount() > 0){
            for (Player p : gameData.getPlayers())
                p.getTank().draw(mCanvas,(p.getSite()[0]-x)*interval,(p.getSite()[1]-y)*interval,interval,interval);
        }
    }

    private void drawBullet(){
        b = gameData.getBullet();
        if (b.size() != 0){
            b.forEach(bullet -> bullet.draw(mCanvas,(bullet.getX()-x)*interval,(bullet.getY()-y)*interval,interval,interval));
        }
    }
}
