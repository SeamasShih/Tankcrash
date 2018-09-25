package com.honhai.foxconn.tankcrash.Tank.Base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.util.Log;

import com.honhai.foxconn.tankcrash.R;
import com.honhai.foxconn.tankcrash.Tank.Prototype.BasePrototype;

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

        Path path = new Path();
        path.addRect(-30,-40,30,40, Path.Direction.CCW);
        path.moveTo(15,-40);
        path.lineTo(10,-30);
        path.lineTo(-10,-30);
        path.lineTo(-15,-40);
        path.close();
        path.moveTo(-10,30);
        path.lineTo(10,30);
        path.lineTo(15,40);
        path.lineTo(-15,40);
        path.close();

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }

}
