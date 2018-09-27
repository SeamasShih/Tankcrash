package com.honhai.foxconn.tankcrash.TankDrawable.Bullet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;

import com.honhai.foxconn.tankcrash.TankDrawable.Prototype.BulletPrototype;

public class BasicBullet extends BulletPrototype {

    public BasicBullet(){
        recording();
    }

    private int rotation;
    private Picture picture = new Picture();

    private void recording() {
        Canvas canvas = picture.beginRecording(100,100);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.addCircle(0,0,5,Path.Direction.CCW);

        canvas.translate(50,50);
        canvas.drawPath(path,paint);
        picture.endRecording();
    }

    public Picture getPicture() {
        return picture;
    }
}
