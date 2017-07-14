package com.lanma.customviewproject.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/14 14:13
 */

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: CustomViewProject
 * @package: com.lanma.customviewproject.views
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/14 14:13
 * @description: <p>
 * <p>
 * loading view
 * </p>
 */

public class LoadingView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mWidth;
    private int mHeight;
    private int[] mGradientColor = {Color.DKGRAY, Color.GRAY};
    private float[] mGradientPosition = {0.5f, 1.0F};
    private RectF drawArc = new RectF();

    private float animatedValue;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mHeight = getHeight();
        drawArc.left = 10;
        drawArc.top = 10;
        drawArc.right = getWidth() - 10;
        drawArc.bottom = getHeight() - 10;
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int radius = Math.min(mWidth, mHeight) / 2;
        mPaint.setShader(new LinearGradient(0, radius, radius, radius * 2, mGradientColor, mGradientPosition, Shader.TileMode.CLAMP));
        canvas.drawArc(drawArc, 0 + animatedValue, 270 + animatedValue, false, mPaint);
    }
}
