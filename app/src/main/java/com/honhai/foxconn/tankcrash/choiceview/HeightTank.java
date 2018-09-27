package com.honhai.foxconn.tankcrash.choiceview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class HeightTank extends View {
    public HeightTank(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tank = new com.honhai.foxconn.tankcrash.tankdrawable.tank.HeightTank();
    }

    com.honhai.foxconn.tankcrash.tankdrawable.tank.HeightTank tank;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tank.draw(canvas,getWidth()/2,getHeight()/2,getWidth()/2,getHeight()/2);
    }
}
