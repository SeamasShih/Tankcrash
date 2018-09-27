package com.honhai.foxconn.tankcrash.tankdrawable.gun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

import com.honhai.foxconn.tankcrash.tankdrawable.prototype.GunPrototype;

public class LightGun extends GunPrototype{

    public LightGun(){
        recording();
    }

    private int rotation;
    private Picture picture = new Picture();

    private void recording(){
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int r = 15;
        int l = 5;

        Path path = new Path();
        path.addCircle(0,0,r,Path.Direction.CCW);
        path.addRect(-l,-30,l,0, Path.Direction.CCW);

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }
}
