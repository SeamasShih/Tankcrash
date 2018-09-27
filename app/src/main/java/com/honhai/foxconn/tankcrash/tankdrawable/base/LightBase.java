package com.honhai.foxconn.tankcrash.tankdrawable.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

import com.honhai.foxconn.tankcrash.tankdrawable.prototype.BasePrototype;

public class LightBase extends BasePrototype{

    public LightBase(){
        recording();
    }

    private int rotation;
    private Picture picture = new Picture();

    private void recording() {
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0x15,0x4b,0x14));
        paint.setAntiAlias(true);

        int h = 30;
        int w = 27;

        Path path = new Path();
        path.addRect(-w,-h,w,h, Path.Direction.CCW);
        path.addRect(-15,-h,15,-22,Path.Direction.CW);
        path.addRect(-15,22,15,h,Path.Direction.CW);
        path.addArc(-32,-10,-22,10,-90,180);
        path.addArc(22,-10,32,10,90,180);

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }

}
