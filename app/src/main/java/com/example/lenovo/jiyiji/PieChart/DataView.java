package com.example.lenovo.jiyiji.PieChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.HashMap;

public class DataView extends View {
    private Paint mPaint=new Paint();
    private Canvas mCanvas;
    private int centerX,centerY;
    public DataView(Context context) {
        super(context);
    }

    public DataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mCanvas=canvas;
        centerX=getResources().getDisplayMetrics().widthPixels/2;
        centerY=getResources().getDisplayMetrics().heightPixels/2;

        drawPieChart();
        drawPieChartAnno();
    }
    /*
    绘制饼图
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawPieChart(){
        HashMap<String , Float> dataDegree = DataDegree();
        HashMap<String , String> dataColor = DataColor();

        int left = centerX - 300;
        int right = centerX + 300;
        int top = centerY -300 ;
        int bottom  = centerY +300;

        //根据颜色和度数绘制饼状图
        float startAngle = 2f;
        mPaint.setStyle(Paint.Style.FILL);

        for(String key : dataDegree.keySet()){
            mPaint.setColor(Color.parseColor(dataColor.get(key)));
            mCanvas.drawArc(left,top,right,bottom, startAngle,dataDegree.get(key),true,mPaint);
            startAngle += dataDegree.get(key);
        }

    }
    /*
    绘制图例
     */
    private void drawPieChartAnno(){
        HashMap<String,Float> dataDegree=DataDegree();
        HashMap<String,String> dataColor=DataColor();
        int left = 2;
        int right = 50;
        int top = 4;
        int rectHeight = 20;
        int bottom = top+rectHeight;
        int blank = 20;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(20);
        for(String key : dataDegree.keySet()){
            mPaint.setColor(Color.parseColor(dataColor.get(key)));
            mCanvas.drawRect(left,top,right,bottom,mPaint);
            mPaint.setColor(Color.DKGRAY);
            mCanvas.drawText(key+":"+ new DecimalFormat("#.00").format((dataDegree.get(key)+2)/360*100)+"%",right+blank, bottom ,mPaint);
            //更新边界位置
            top = bottom + blank;
            bottom  = top + rectHeight;
        }

    }

    private HashMap<String,Float> DataDegree(){
        HashMap<String,Float> data=new HashMap<>();
        data.put("苹果", 40f);
        data.put("西瓜", 41f);
        data.put("香蕉", 59f);
        data.put("脐橙", 80f);
        data.put("菠萝",140f);
        return data;

    }
    private HashMap<String,String> DataColor(){
        HashMap<String,String> data=new HashMap<>();
        data.put("苹果", "#6600ff");
        data.put("西瓜", "#cc00ff");
        data.put("香蕉","#9933ff");
        data.put("脐橙", "#ff9900");
        data.put("菠萝","#9999ff");
        return data;
    }
}
