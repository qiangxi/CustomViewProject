package com.lanma.customviewproject.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 作者 任强强 on 2017/2/14 15:35.
 * 水波纹效果(二阶贝塞尔曲线的应用)
 * 实现要点:1,二阶贝塞尔曲线的绘制
 *         2,横向属性动画的位移距离要和波长(两个半圆的长度)一致,不然衔接不上
 */

public class WaterRipplesView extends View {
    private Paint mPaint;
    private Path mPath;
    private int dx;
    private int dy;
    private boolean animIsStart = false;
    private int WaveformCount = 2;//波形数量
    private int WaveformLength = 200;//波形长度
    private ValueAnimator animator;//横向
    private ValueAnimator animator1;//竖向
    private int w;//view宽
    private int h;//view高

    public WaterRipplesView(Context context) {
        this(context, null);
    }

    public WaterRipplesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterRipplesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    private void initConfig() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#35A7FF"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getWidth();
        h = getHeight();
        WaveformLength = w / WaveformCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-WaveformLength * 2 + dx, h / 3 + dy);
        for (int i = 0; i < WaveformCount; i++) {
            mPath.rQuadTo(WaveformLength / 2, 100, WaveformLength, 0);
            mPath.rQuadTo(WaveformLength / 2, -100, WaveformLength, 0);
        }
        mPath.lineTo(w, h);
        mPath.lineTo(0, h);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnim() {
        //横向
        animator = ValueAnimator.ofInt(0, WaveformLength * 2);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
        //竖向
        animator1 = ValueAnimator.ofInt(0, h * 2 / 3 + 100);
        animator1.setDuration(5000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator1.start();
        animIsStart = true;
    }

    public void stopAnim() {
        if (null != animator && animator.isRunning()) {
            animator.cancel();
        }
        if (null != animator1 && animator1.isRunning()) {
            animator1.cancel();
        }
        animIsStart = false;
    }

    public boolean isAnimIsStart() {
        return animIsStart;
    }
}
