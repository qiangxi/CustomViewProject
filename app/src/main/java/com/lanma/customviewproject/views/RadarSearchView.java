package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.lanma.customviewproject.R;

/**
 * 作者 任强强 on 2016/8/20 12:55.
 * 类似雷达扫描效果
 */
public class RadarSearchView extends View {
    private final int mSmallestRadius = getScreenWidth() / 10;
    private Paint mCirclePaint;//圆圈画笔
    private Paint mInsideCirclePaint;//内圆画笔
    private Paint mLinePaint;//线画笔
    private Paint mTextPaint;//文本画笔
    private int mCircleColor = Color.RED;
    private int mLineColor = Color.RED;
    private int mTextColor = Color.RED;
    private String mInsideText = "2km";//线中间的文本
    private int mRadius = mSmallestRadius;//圆半径==线的长度
    private float mRotateDegrees = 0;
    private boolean isShowing = false;

    public RadarSearchView(Context context) {
        this(context, null);
    }

    public RadarSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadarSearchView);

        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.RadarSearchView_radiusLevel:
                    setRadiusLevel(a.getInt(attr, 1));
                    break;
                case R.styleable.RadarSearchView_insideText:
                    mInsideText = a.getString(attr);
                    break;
                case R.styleable.RadarSearchView_insideTextColor:
                    mTextColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.RadarSearchView_insideLineColor:
                    mLineColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.RadarSearchView_circleColor:
                    mCircleColor = a.getColor(attr, Color.RED);
                    break;
            }
        }
        a.recycle();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(dpToPx(2));
        mCirclePaint.setAntiAlias(true);

        mInsideCirclePaint = new Paint();
        mInsideCirclePaint.setColor(mCircleColor);
        mInsideCirclePaint.setStyle(Paint.Style.FILL);
        mInsideCirclePaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(dpToPx(1));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(spToPx(14));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowing()) {
            //绘制外圆圈
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mCirclePaint);
            //绘制内圆点
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, dpToPx(3), mInsideCirclePaint);
            //绘制文本
            canvas.drawText(mInsideText, getWidth() / 2 - getTextWidth(mInsideText) / 2, getHeight() / 2 + mRadius / 2 + getTextHeight(mInsideText) / 2, mTextPaint);
            //绘制直线
            canvas.save();
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + mRadius * (float) Math.cos(mRotateDegrees * Math.PI / 180), getHeight() / 2 - mRadius * (float) Math.sin(mRotateDegrees * Math.PI / 180), mLinePaint);
            canvas.rotate(mRotateDegrees);
            canvas.restore();
            mRotateDegrees += 1;
            invalidate();
        }
    }

    private int dpToPx(int dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private int spToPx(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int getTextWidth(String text) {
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBound);
        return textBound.width();
    }

    private int getTextHeight(String text) {
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBound);
        return textBound.height();
    }

    private int getScreenWidth() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 只能设置1-10,10个级别(1的半径最小,10的半径最大)
     * 设置其他数值直接抛异常
     */
    public void setRadiusLevel(int radiusLevel) {
        if (radiusLevel < 1 || radiusLevel > 10) {
            throw new IllegalArgumentException("非法参数,参数只能在1-10之间选择");
        }
        switch (radiusLevel) {
            case 1:
                this.mRadius = mSmallestRadius;
                break;
            case 2:
                this.mRadius = mSmallestRadius + dpToPx(10);
                break;
            case 3:
                this.mRadius = mSmallestRadius + dpToPx(20);
                break;
            case 4:
                this.mRadius = mSmallestRadius + dpToPx(30);
                break;
            case 5:
                this.mRadius = mSmallestRadius + dpToPx(40);
                break;
            case 6:
                this.mRadius = mSmallestRadius + dpToPx(50);
                break;
            case 7:
                this.mRadius = mSmallestRadius + dpToPx(60);
                break;
            case 8:
                this.mRadius = mSmallestRadius + dpToPx(70);
                break;
            case 9:
                this.mRadius = mSmallestRadius + dpToPx(80);
                break;
            case 10:
                this.mRadius = mSmallestRadius + dpToPx(90);
                break;
        }
        invalidate();
    }

    public void setInsideText(String insideText) {
        this.mInsideText = insideText;
        invalidate();
    }

    public void setInsideTextColor(int insideTextColor) {
        this.mTextColor = insideTextColor;
        invalidate();
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        invalidate();
    }

    public void setCircleColor(int circleColor) {
        this.mCircleColor = circleColor;
        invalidate();
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void dismiss() {
        this.isShowing = false;
        mRotateDegrees = 0;
        invalidate();
    }

    public void show() {
        this.isShowing = true;
        invalidate();
    }
}
