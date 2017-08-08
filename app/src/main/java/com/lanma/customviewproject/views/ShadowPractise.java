package com.lanma.customviewproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/8/8 15:16
 */

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: CustomViewProject
 * @package: com.lanma.customviewproject.views
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/8/8 15:16
 * @description: <p>
 * <p>
 * </p>
 */

public class ShadowPractise extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ShadowPractise(Context context) {
        this(context, null);
    }

    public ShadowPractise(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowPractise(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        ViewCompat.setElevation(this, 5000);
//        mPaint.set
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
//        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 10, mPaint);
    }
}
