package com.honhai.foxconn.tankcrash.ButtonView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DirectorKey extends View {
    public DirectorKey(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setAntiAlias(true);
    }

    Path path;
    Paint white;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPath();
    }

    private void setPath() {
        int w = getWidth()/2;
        int h = getHeight()/2;
        path.reset();
        path.moveTo(0,-h);
        path.lineTo(-w,0);
        path.lineTo(w,0);
        path.close();
        path.addRect(-w/3,0,w/3,h, Path.Direction.CCW);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(canvas.getWidth()/2,canvas.getHeight()*2/3);
        canvas.scale(0.5f,0.9f);
        canvas.drawPath(path,white);
        canvas.restore();
    }
}
