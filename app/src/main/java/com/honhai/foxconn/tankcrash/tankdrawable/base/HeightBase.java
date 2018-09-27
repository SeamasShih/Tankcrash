package com.honhai.foxconn.tankcrash.tankdrawable.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

import com.honhai.foxconn.tankcrash.tankdrawable.prototype.BasePrototype;

public class HeightBase extends BasePrototype {

    public HeightBase(){
        recording();
    }

    private int rotation;
    private Picture picture = new Picture();

    private void recording() {
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x15,0x4b,0x14));
        paint.setAntiAlias(true);

        int h = 45;
        int k = 30;
        int w = 27;
        int m = 15;
        int n = 10;

        Path path = new Path();
        path.addRect(-w,-h,w,h, Path.Direction.CCW);
        path.moveTo(m,-h);
        path.lineTo(n,-k);
        path.lineTo(-n,-k);
        path.lineTo(-m,-h);
        path.close();
        path.moveTo(-n,k);
        path.lineTo(n,k);
        path.lineTo(m,h);
        path.lineTo(-m,h);
        path.close();

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }

}
