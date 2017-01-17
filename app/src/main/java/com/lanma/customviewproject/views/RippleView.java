package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.lanma.customviewproject.R;

/**
 * 作者 qiang_xi on 2016/8/18 13:24.
 * RippleView :一个圆圈,半径慢慢变大,类似于由内而外的扫描效果.
 */
public class RippleView extends View {
    private int DefaultRippleVelocity = 2;
    private Paint mCirclePaint;//圆圈画笔
    private int mRippleColor = Color.RED;//波纹颜色
    private boolean isRippleSpreading;//波纹是否正在扩散
    private int mRippleVelocity = DefaultRippleVelocity;//波纹扩散速率 [1-10]
    private int mRippleRadius = 0;//波纹的半径
    private int mRippleWidth = dpToPx(3);

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.RippleView_rippleViewColor:
                    mRippleColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.RippleView_rippleVelocity:
                    mRippleVelocity = a.getInteger(attr, 5);
                    break;
                case R.styleable.RippleView_rippleWidth:
                    mRippleWidth = a.getInteger(attr, dpToPx(3));
                    break;
            }
        }
        a.recycle();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mRippleWidth);
        mCirclePaint.setColor(mRippleColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        //计算宽度
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 100;
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(widthSize, width);
            }
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isRippleSpreading) {
            mRippleRadius += mRippleVelocity;
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, mRippleRadius, mCirclePaint);
            if (mRippleRadius > getScreenWidth() / 2 - 5) {
                mRippleRadius = 0;
            }
            invalidate();
        }
    }

    /**
     * 开启波纹扩散
     */
    public void startRippleSpread() {
        isRippleSpreading = true;
        invalidate();
    }

    /**
     * 关闭波纹扩散
     */
    public void closeRippleSpread() {
        isRippleSpreading = false;
        mRippleRadius = DefaultRippleVelocity;
        invalidate();
    }

    /**
     * 波纹是否正在扩散
     */
    public boolean isRippleSpreading() {
        return isRippleSpreading;
    }

    /**
     * 设置波纹扩散速率,推荐1-10即可,太小或太大看起来很不好看
     * 默认5
     */
    public void setRippleVelocity(int rippleVelocity) {
        this.mRippleVelocity = rippleVelocity;
        invalidate();
    }

    public int getRippleVelocity() {
        return mRippleVelocity;
    }

    /**
     * 设置波纹线的宽度
     *
     * @param rippleWidth 单位dp
     */
    public void setRippleWidth(int rippleWidth) {
        this.mRippleWidth = dpToPx(rippleWidth);
        invalidate();
    }

    public int getRippleWidth() {
        return mRippleWidth;
    }

    /**
     * 设置波纹颜色
     */
    public void setRippleColor(int rippleColor) {
        this.mRippleColor = rippleColor;
        invalidate();
    }

    public int getRippleColor() {
        return mRippleColor;
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * dp转px
     */
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dp, getContext().getResources().getDisplayMetrics());
    }
}
