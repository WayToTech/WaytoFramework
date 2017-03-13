package com.wayto.map.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @Package: com.yunwei.map.widget
 * @Description:
 * @author: Aaron
 * @date: 2016-06-30
 * @Time: 15:33
 * @version: V1.0
 */
public class CircleView extends View {

    private float x=10,y=10;
    public CircleView(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(120, 20, 20,paint);
    }

    public void setCircleX(float x){
        this.x=x;
    }

    public void setCircleY(float y){
        this.y=y;
    }
}
