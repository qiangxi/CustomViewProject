package com.lanma.customviewproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lanma.customviewproject.utils.ScreenUtils;

import java.util.Random;


/**
 * Created by qiang_xi on 2017/1/30 15:17.
 * 音频条形图
 */

public class AudioBarChart extends View {
    private Paint mPaint;
    private int barChartCount = 10;//条形图数量
    private float barChartWidth = ScreenUtils.dpToPx(getContext(), 20);//单个条形图宽度
    private Random mRandom;

    public AudioBarChart(Context context) {
        this(context, null);
    }

    public AudioBarChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRandom = new Random();
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = ScreenUtils.getScreenWidth(getContext(), ScreenUtils.TYPE_PX);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = ScreenUtils.dpToPx(getContext(), 300);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        for (int i = 0; i < barChartCount; i++) {
            canvas.translate(20, 0);
            canvas.drawRect(barChartWidth * i, mRandom.nextInt(180) + 20,  barChartWidth * (i + 1), getHeight(), mPaint);
        }
//        canvas.restore();
    }

    public void setBarChartCount(int barChartCount) {
        this.barChartCount = barChartCount;
    }
}
