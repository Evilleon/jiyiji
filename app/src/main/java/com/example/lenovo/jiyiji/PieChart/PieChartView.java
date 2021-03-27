package com.example.lenovo.jiyiji.PieChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.example.lenovo.jiyiji.R;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {

    private int MIN_WIDTH , MIN_HEIGHT , viewWidth , viewHeight , pieRadius;
    private final float disAngle = 4;//2-10之间为最优动画效果
    private int bgColor = R.color.white;//底背景和上层圆形的颜色
    private List<ArcBean> list = new ArrayList<>();
    private float startAngle = -90;//图形起始角度
    private float currentAngle = -90;//当前画了多少角度
    private float leftAngle = 360;//剩余多少角度
    private boolean isStartAnim = false;
    private boolean isInvilidate = true;

    public PieChartView(Context context) {
        this(context , null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        MIN_WIDTH = dp2px(context , 100);
        MIN_HEIGHT = dp2px(context , 100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = measureSize(widthMeasureSpec , MIN_WIDTH);
        viewHeight = measureSize(heightMeasureSpec , MIN_HEIGHT);
        pieRadius = Math.min(viewWidth , viewHeight)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(getResources().getColor(bgColor));
        if (isStartAnim){
            drawViewArc(canvas);
            drawBackgroundCircle(canvas);
        }else {
            drawViewArc(canvas);
        }
    }

    public int dp2px(Context context , int dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density +0.5f);
    }

    private int measureSize(int measureSpec , int defaultValue){
        int resultSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY){
            resultSize = size;
        }else {
            resultSize = defaultValue;
        }
        return resultSize;
    }

    private Paint setPaintSrc(int color){
        Paint paintSrc = new Paint();
        paintSrc.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintSrc.setStyle(Paint.Style.FILL);
        paintSrc.setColor(getResources().getColor(color));
        return paintSrc;
    }

    /**
     * 对数据进行处理-->以360度折算成比例
     * @param list
     */
    private void dealArcAngle(List<ArcBean> list){
        float finalValue = 0;
        for (int i = 0 ; i < list.size() ; i ++){
            ArcBean arcBean = list.get(i);
            finalValue = finalValue + arcBean.getValue();
        }
        float density = 360 * 10000 / finalValue;
        float resultDensity = density/10000;
        for (int i = 0 ; i < list.size() ; i ++){
            ArcBean arcBean = list.get(i);
            float resultAngle = resultDensity * arcBean.getValue();
            arcBean.setValue(resultAngle);
        }
    }

    /**
     * 饼状图
     * @param canvas
     */
    private void drawViewArc(Canvas canvas){
        for (int i = 0 ; i < list.size() ; i ++){
            ArcBean arcBean = list.get(i);
            float sweepAngle = arcBean.getValue();
            Paint paint = setPaintSrc(arcBean.getColor());
            int radius = dp2px(getContext() , arcBean.getRadius());
            if (radius > pieRadius - 5){
                radius = pieRadius - 5;
            }
            RectF rectF = new RectF(pieRadius - radius ,
                    pieRadius - radius ,
                    pieRadius + radius ,
                    pieRadius + radius);
            canvas.drawArc(rectF , startAngle , sweepAngle , true , paint);
            startAngle = startAngle +sweepAngle;
        }
    }

    /**
     * 画上层遮盖的圆形
     * @param canvas
     */
    private void drawBackgroundCircle(Canvas canvas){
        RectF rectF = new RectF(0 , 0 , pieRadius * 2 , pieRadius * 2);
        Paint paint = setPaintSrc(bgColor);
        canvas.drawArc(rectF , currentAngle , leftAngle , true , paint);
        float angle = 270 - currentAngle;
        float tempAngle = 0;
        if (angle > 0){
            if (angle > disAngle){
                currentAngle = currentAngle + disAngle;
                tempAngle = disAngle;
            }else {
                currentAngle = currentAngle + angle;
                tempAngle = angle;
            }
        }else {
            isInvilidate = false;
        }
        if (isInvilidate){
            invalidate();
            leftAngle = leftAngle - tempAngle;
        }
    }

    /**
     * @param dataList 资源数据
     * @param isShowAnim 是否开启动画
     */
    public void setData(List<ArcBean> dataList , int bgColor , boolean isShowAnim){
        list.clear();
        list.addAll(dataList);
        dealArcAngle(this.list);
        this.bgColor = bgColor;
        isStartAnim = isShowAnim;
        if (isShowAnim){
            startAngle = -90;
            currentAngle = -90;
            leftAngle = 360;
            isInvilidate = true;
        }
        invalidate();
    }

}
