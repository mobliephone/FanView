package com.sh.fanview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.NumberFormat;
import java.util.List;

/**
 * @Description: 扇形图
 * @Author: cgw
 * @CreateDate: 2019/8/13 10:14
 */
public class FanChartView extends View {

    //圆弧、指示线、文本画笔
    private Paint mPaint, linePaint, textPaint;
    //布局宽高
    private int mWidth,mHeight;
    //数据源
    private List<FanBean> mData;
    //颜色
    private int[] mColor= {Color.parseColor("#FFB6C1"), Color.parseColor("#DC143C")
            , Color.parseColor("#4169E1"), Color.parseColor("#87CEEB")
            , Color.parseColor("#00CED1"), Color.parseColor("#7FFFAA")
            , Color.parseColor("#FFFF00")};

    public FanChartView(Context context) {
        this(context, null);
        initPaint();
    }

    public FanChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
    }

    public FanChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setStrokeWidth(3);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
//        linePaint.setColor(Color.parseColor("#000000"));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(16f);
        textPaint.setColor(Color.parseColor("#000000"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取空间宽、高
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //支持wrap_content

        //测量模式
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        //测量值
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        //设置默认宽高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measureWH = (int) (Math.min(measuredWidth, measuredHeight) / 1.1);

        if(wMode == MeasureSpec.AT_MOST && hMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(measureWH,measureWH);
        } else if (wMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(measureWH,hSize);
        } else if (hMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(wSize,measureWH);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null || mData.size() == 0) return;

        //开始弧度
        float currentAngle = 0f;
        //绘制原点中心区域
        canvas.translate(mWidth/2f,mHeight/2f);
        //圆弧半径
        float radius = (Math.min(mWidth,mHeight)/2f)*0.5f;
        Log.d("logInfo", HollowCircleView.class.getSimpleName()+"---radius---->"+radius);
        //绘制矩形
        RectF rectF = new RectF(-radius,-radius,radius,radius);

        for (int i = 0; i < mData.size(); i++) {
            mPaint.setColor(mColor[i]);

            FanBean fanBean = mData.get(i);
            float radian = fanBean.getRadian();
            //绘制圆弧
            canvas.drawArc(rectF,currentAngle, radian,true,mPaint);

            //直线角度
            float lineAngle = radian / 2 + currentAngle;
            //获取x，y坐标
            float startX = (float) (Math.cos(lineAngle *Math.PI/180)*radius);
            float startY = (float) (Math.sin(lineAngle *Math.PI/180)*radius);
            float endX = (float) (Math.cos(lineAngle *Math.PI/180)*(radius+20));
            float endY = (float) (Math.sin(lineAngle *Math.PI/180)*(radius+20));
            float endTextX = (float) (Math.cos(lineAngle *Math.PI/180)*(radius+35));
            float endTextY = (float) (Math.sin(lineAngle *Math.PI/180)*(radius+35));

            Log.d("logInfo", HollowCircleView.class.getSimpleName()+"---x"+i+"---->"+startX+"---"+fanBean.getName());
            Log.d("logInfo", HollowCircleView.class.getSimpleName()+"---y"+i+"---->"+startY+"---"+fanBean.getName());

            //绘制指示线
            Path path = new Path();
            path.moveTo(startX,startY);
            path.lineTo(endX,endY);
            if (endX < 0){
                path.lineTo(endX-20,endY);
            } else {
                path.lineTo(endX+20,endY);
            }
            linePaint.setColor(mColor[i]);
            canvas.drawPath(path, linePaint);

            //获取格式化对象
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMinimumFractionDigits(2);
            String format = nf.format(fanBean.getPercentage());
            //绘制文字

            if (endTextX < 0){
                canvas.drawText(fanBean.getName()+format, endX-140,endY+5, textPaint);
            } else {
                canvas.drawText(fanBean.getName()+format, endX+25,endY+5, textPaint);
            }

            //角度
            currentAngle += radian;
        }

    }


    /**
     * 设置数据
     * @param mData
     */
    public void setData(List<FanBean> mData){
        this.mData = mData;
        initData(mData);
        invalidate();
    }

    /**
     * 格式化弧度数据
     * @param mData
     */
    private void initData(List<FanBean> mData){
        for (int i = 0; i < mData.size(); i++) {
            FanBean fanBean = mData.get(i);
            float radian = fanBean.getPercentage()*360;
            fanBean.setRadian(radian);
        }
        invalidate();
    }



}
