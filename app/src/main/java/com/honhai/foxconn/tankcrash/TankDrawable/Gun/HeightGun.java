package com.honhai.foxconn.tankcrash.TankDrawable.Gun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

import com.honhai.foxconn.tankcrash.TankDrawable.Prototype.GunPrototype;

public class HeightGun extends GunPrototype {
    public HeightGun(){
        recording();
    }

    private int rotation;
    private Picture picture = new Picture();

    private void recording(){
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int w = 15;
        int h = 10;
        int m = 7;

        Path path = new Path();
        path.addRect(-w,-h,w,h,Path.Direction.CW);
        path.addRect(-m,-h,m,0,Path.Direction.CCW);

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }
}
