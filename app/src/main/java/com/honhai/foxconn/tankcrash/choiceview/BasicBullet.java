package com.honhai.foxconn.tankcrash.choiceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BasicBullet extends View {
    public BasicBullet(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bullet = new com.honhai.foxconn.tankcrash.tankdrawable.bullet.BasicBullet();
        picture = bullet.getPicture();
    }

    com.honhai.foxconn.tankcrash.tankdrawable.bullet.BasicBullet bullet;
    Picture picture;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPicture(picture,new Rect(0,0,getWidth(),getHeight()));
    }
}


