package com.example.lenovo.jiyiji.Utils;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyCircleDrawable extends BitmapDrawable {
    private final Paint paint;//画笔


    public MyCircleDrawable(Resources resources,Bitmap bitmap){
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        paint.setColor(Color.RED);
    }
    /*
    这个方法是指drawbale将被绘制在画布上的区域
     */
    //左上右下
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
    }

    /*
       这是我们的核心方法,绘制我们想要的图片
        */
    @Override
    public void draw(Canvas canvas) {
        int width = getBitmap().getWidth();
        int height = getBitmap().getHeight();
        int radius = Math.min(width/2, height/2);
        int x = (width>radius+radius)?width/2:radius;
        int y = (height>radius+radius)?height/2:radius;
        canvas.drawCircle(x,y,radius,paint);
    }




}
