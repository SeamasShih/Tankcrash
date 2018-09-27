package com.honhai.foxconn.tankcrash.TankDrawable.Tank;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.honhai.foxconn.tankcrash.TankDrawable.Base.LightBase;
import com.honhai.foxconn.tankcrash.TankDrawable.Gun.LightGun;
import com.honhai.foxconn.tankcrash.TankDrawable.Prototype.BasePrototype;
import com.honhai.foxconn.tankcrash.TankDrawable.Prototype.GunPrototype;
import com.honhai.foxconn.tankcrash.TankDrawable.Prototype.TankPrototype;

public class LightTank extends TankPrototype{
    public LightTank() {

    }

    BasePrototype base = new LightBase();
    GunPrototype gun = new LightGun();
    private int gunRotation = 0;
    private int baseRotation = 0;
    int type = 0;

    public int getType() {
        return type;
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
        canvas.restore();
    }

}
