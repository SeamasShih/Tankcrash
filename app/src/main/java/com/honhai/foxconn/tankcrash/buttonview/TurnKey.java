package com.honhai.foxconn.tankcrash.buttonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TurnKey extends View {
    public TurnKey(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        triangle = new Path();
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.STROKE);
        white.setStrokeWidth(d);
        fill = new Paint();
        fill.setColor(Color.LTGRAY);
        fill.setAntiAlias(true);
        fill.setStyle(Paint.Style.FILL);
    }

    Path path;
    Path triangle;
    Paint white;
    Paint fill;

    int d = 10;
    int e = 10;


    private void setPath() {
        int w = getWidth()/2;
        int h = getHeight()/2;
        path.reset();
        path.addArc(-w+d+e,-h+d+e,w-d-e,h-d-e,-90,180);
        triangle.moveTo(0,h);
        triangle.lineTo(-w/2,h-e-d);
        triangle.lineTo(0,h-2*(e+d));
        triangle.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setPath();

        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawPath(path,white);
        canvas.drawPath(triangle,fill);
        canvas.restore();
    }
}
