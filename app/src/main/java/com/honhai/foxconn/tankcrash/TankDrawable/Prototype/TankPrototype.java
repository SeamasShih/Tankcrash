package com.honhai.foxconn.tankcrash.TankDrawable.Prototype;

import android.graphics.Canvas;

public abstract class TankPrototype {
    abstract public int getType();

    abstract public void turnBaseRight();

    abstract public void turnBaseLeft();

    abstract public int getBaseRotation();

    abstract public void turnGunRight();

    abstract public void turnGunLeft();

    abstract public int getGunRotation();

    abstract public void draw(Canvas canvas, float cx, float cy, int w, int h);
}
