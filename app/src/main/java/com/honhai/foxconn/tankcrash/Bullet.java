package com.honhai.foxconn.tankcrash;


import android.graphics.Canvas;
import android.graphics.RectF;

import com.honhai.foxconn.tankcrash.tankdrawable.bullet.BasicBullet;
import com.honhai.foxconn.tankcrash.tankdrawable.prototype.BulletPrototype;

public class Bullet {

    public Bullet(int order , float x , float y){
        this.order = order;
        this.x = x;
        this.y = y;
    }

    private float x;
    private float y;
    private int order;
    private BulletPrototype bullet = new BasicBullet();
    private int type;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void draw(Canvas canvas, float cx, float cy, int w, int h) {
        canvas.drawPicture(bullet.getPicture(), new RectF(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2));
    }

}
