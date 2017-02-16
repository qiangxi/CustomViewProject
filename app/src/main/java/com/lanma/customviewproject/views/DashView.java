package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lanma.customviewproject.R;


/**
 * 作者 任强强 on 2017/2/8 12:32.
 * 虚线View,一般用于优惠券中的分隔线等
 * 可在实际项目中使用
 */

public class DashView extends View {
    //常量
    // (view属性)
    public static final int HORIZONTAL = 0;//垂直
    public static final int VERTICAL = 1;//水平
    public static final int RECTANGLE = 0;//矩形
    public static final int ROUND = 1;//圆角矩形
    private static final int[] Direction = {HORIZONTAL, VERTICAL};
    private static final int[] Shape = {RECTANGLE, ROUND};

    private int mDashColor = Color.BLACK;//虚线中每个点的颜色
    private int mDashCount = 5;//虚线中点的数量
    private int mDashDirection = VERTICAL;//虚线的方向(水平/垂直)
    private int mDashHeight;//虚线中每个点的高度(矩形有效)
    private int mDashWidth;//虚线中每个点的宽度(矩形有效)
    private int mDashShape = RECTANGLE;//虚线的形状(圆形/矩形)
    private int mDashRange = dpToPx(5);//虚线中每个点之间的间隔

    private Paint mPaint;
    private RectF mRect;

    public DashView(Context context) {
        this(context, null);
    }

    public DashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.DashView_dashColor:
                    mDashColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.DashView_dashCount:
                    mDashCount = a.getInteger(attr, 5);
                    break;
                case R.styleable.DashView_dashDirection:
                    mDashDirection = Direction[a.getInt(R.styleable.DashView_dashDirection, 0)];
                    break;
                case R.styleable.DashView_dashHeight:
                    mDashHeight = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.DashView_dashWidth:
                    mDashWidth = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.DashView_dashShape:
                    mDashShape = Shape[a.getInt(R.styleable.DashView_dashShape, 0)];
                    break;
                case R.styleable.DashView_dashRange:
                    mDashRange = a.getDimensionPixelSize(attr, dpToPx(3));
                    break;
            }
        }
        a.recycle();
        initConfig();
    }

    private void initConfig() {
        //rect
        mRect = new RectF();
        //Paint
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mDashColor);
        //水平
        if (mDashDirection == HORIZONTAL) {
            if (mDashHeight == 0) {
                mDashHeight = dpToPx(2);
            }
            if (mDashWidth == 0) {
                mDashWidth = dpToPx(15);
            }
        }
        //垂直
        else {
            if (mDashHeight == 0) {
                mDashHeight = dpToPx(15);
            }
            if (mDashWidth == 0) {
                mDashWidth = dpToPx(2);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int totalWidth = widthSize;
        int totalHeight = heightSize;

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            //水平
            if (mDashDirection == HORIZONTAL) {
                //计算得到的刚好包裹住内容的大小
                int w = getPaddingLeft() + getPaddingRight() + mDashCount * mDashWidth + (mDashCount - 1) * mDashRange;
                //与父View传递过来的宽度做对比
                totalWidth = Math.min(w, widthSize);
            }
            //垂直
            else {
                //与父View传递过来的宽度做对比
                totalWidth = Math.min(getPaddingLeft() + getPaddingRight() + mDashWidth, widthSize);
            }
        }
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            //水平
            if (mDashDirection == HORIZONTAL) {
                //与父View传递过来的宽度做对比
                totalHeight = Math.min(getPaddingTop() + getPaddingBottom() + mDashHeight, heightSize);
            }
            //垂直
            else {
                //计算得到的刚好包裹住内容的大小
                int h = getPaddingTop() + getPaddingBottom() + mDashCount * mDashHeight + (mDashCount - 1) * mDashRange;
                //与父View传递过来的宽度做对比
                totalHeight = Math.min(h, heightSize);
            }
        }
        setMeasuredDimension(totalWidth, totalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //水平
        canvas.save();
        if (mDashDirection == HORIZONTAL) {
            mRect.left = 0;
            mRect.top = (getHeight() - mDashHeight) / 2;
            mRect.right = mDashWidth;
            mRect.bottom = (getHeight() + mDashHeight) / 2;
            canvas.translate(getPaddingLeft(), 0);//添加左侧padding
            for (int i = 0; i < mDashCount; i++) {
                if (mDashShape == RECTANGLE) {
                    canvas.drawRect(mRect, mPaint);
                } else {
                    canvas.drawRoundRect(mRect, mDashHeight / 2, mDashHeight / 2, mPaint);
                }
                canvas.translate(mDashRange + mDashWidth, 0);
            }
            canvas.translate(getPaddingRight(), 0);//添加右侧padding
        }
        //垂直
        else {
            mRect.left = (getWidth() - mDashWidth) / 2;
            mRect.top = 0;
            mRect.right = (getWidth() + mDashWidth) / 2;
            mRect.bottom = mDashHeight;
            canvas.translate(0, getPaddingTop());
            for (int i = 0; i < mDashCount; i++) {
                if (mDashShape == RECTANGLE) {
                    canvas.drawRect(mRect, mPaint);
                } else {
                    canvas.drawRoundRect(mRect, mDashWidth / 2, mDashWidth / 2, mPaint);
                }
                canvas.translate(0, mDashRange + mDashHeight);
            }
            canvas.translate(0, getPaddingBottom());
        }
        canvas.restore();
    }

    private int dpToPx(int dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
