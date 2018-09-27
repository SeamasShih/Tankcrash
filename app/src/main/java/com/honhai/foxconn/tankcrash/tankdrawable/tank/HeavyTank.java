package com.honhai.foxconn.tankcrash.tankdrawable.tank;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.honhai.foxconn.tankcrash.tankdrawable.base.HeavyBase;
import com.honhai.foxconn.tankcrash.tankdrawable.gun.HeavyGun;
import com.honhai.foxconn.tankcrash.tankdrawable.prototype.BasePrototype;
import com.honhai.foxconn.tankcrash.tankdrawable.prototype.GunPrototype;
import com.honhai.foxconn.tankcrash.tankdrawable.prototype.TankPrototype;

public class HeavyTank extends TankPrototype {
    BasePrototype base = new HeavyBase();
    GunPrototype gun = new HeavyGun();
    private int gunRotation = 0;
    private int baseRotation = 0;
    int type = 1;

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
