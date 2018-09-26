package com.honhai.foxconn.tankcrash.Tank.Gun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

import com.honhai.foxconn.tankcrash.Tank.Prototype.GunPrototype;

public class HeavyGun extends GunPrototype {
    public HeavyGun(){
        recording();
    }

    private int rotation;
    private Picture picture = new Picture();

    private void recording(){
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x20,0x6b,0x20));
        paint.setAntiAlias(true);

        int w = 20;
        int h = (int) (w/2*1.732);
        int l = 5;

        Path path = new Path();
        path.moveTo(-w,0);
        path.lineTo(-w/2,h);
        path.lineTo(w/2,h);
        path.lineTo(w,0);
        path.lineTo(w/2,-h);
        path.lineTo(-w/2,-h);
        path.close();
        path.addRect(-l,-50,l,0, Path.Direction.CCW);

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }
}
