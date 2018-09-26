package com.honhai.foxconn.tankcrash.Tank.Tank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.honhai.foxconn.tankcrash.Tank.Base.HeightBase;
import com.honhai.foxconn.tankcrash.Tank.Base.LightBase;
import com.honhai.foxconn.tankcrash.Tank.Gun.HeightGun;
import com.honhai.foxconn.tankcrash.Tank.Gun.LightGun;
import com.honhai.foxconn.tankcrash.Tank.Prototype.BasePrototype;
import com.honhai.foxconn.tankcrash.Tank.Prototype.GunPrototype;
import com.honhai.foxconn.tankcrash.Tank.Prototype.TankPrototype;

public class HeightTank extends TankPrototype {

    public HeightTank(){
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
    }

    BasePrototype base = new HeightBase();
    GunPrototype gun = new HeightGun();
    private int gunRotation = 0;
    private int baseRotation = 0;
    private float l = 2;
    private int k = 50;
    private Paint paint = new Paint();
    int type = 2;

    public int getType() {
        return type;
    }

    public void raiseGun() {
        if (k >= 50){
            k = 50;
            return;
        }
        k+=5;
    }
    public void lowerGun() {
        if (k <= 20){
            k = 20;
            return;
        }
        k-=5;
    }

    public int getGunLength() {
        return k;
    }

    public void turnBaseRight() {
        baseRotation = (baseRotation +90) % 360;
    }

    public void turnBaseLeft() {
        baseRotation = (baseRotation -90) % 360;
    }

    public int getBaseRotation() {
        return baseRotation;
    }

    public void turnGunRight() {
        gunRotation = (gunRotation +90) % 360;
    }

    public void turnGunLeft() {
        gunRotation = (gunRotation -90) % 360;
    }

    public int getGunRotation(){
        return gunRotation;
    }

    public void draw(Canvas canvas, float cx, float cy, int w, int h) {
        canvas.save();
        canvas.rotate(baseRotation);
        canvas.drawPicture(base.getPicture(), new RectF(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2));
        canvas.restore();
        canvas.save();
        canvas.rotate(gunRotation);
        canvas.drawPicture(gun.getPicture(), new RectF(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2));
        canvas.drawRect(cx-l*w/100,cy-k*h/100,cx+l*w/100,cy,paint);
        canvas.restore();
    }

}
