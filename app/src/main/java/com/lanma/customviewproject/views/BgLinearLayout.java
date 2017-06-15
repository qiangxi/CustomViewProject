package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.lanma.customviewproject.R;

/**
 * 自定义根布局,3种方式：
 * 1.指定白色背景百分比
 * 2.指定白色背景高度
 * 3.以某个指定view为锚点，从该view高度的一半为起点一直到BgLinearLayout的bottom。（推荐，此种方式绘制的白色背景高度很精准）
 * 第三种方式使用方法：为BgLinearLayout的直接子(View/ViewGroup)设置tag属性，
 * 使用<code> android:tag="@{@string/anchor_tag}"</code>,设置tag之后,BgLinearLayout会自动找到该view，
 * 计算白色背景的top并进行绘制。这种设置tag的方式是需要结合dataBinding使用。
 * 理论上，可以为BgLinearLayout的每个子view设置tag属性，但是tag值为ANCHOR_TAG的有且只能有一个，如果设置多个，只有第一个有效
 * <p>
 * </p>
 */

public class BgLinearLayout extends LinearLayout {
    private float mBgHeightPercent = 0F;//bg高度百分比
    private int mBgColor = Color.WHITE;//背景颜色
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mBgRectF = new RectF();
    private float mBgHeight = 0F;//bg高度,dp
    private static final String ANCHOR_TAG = "AnchorTag";//锚点tag
    private int mRealTop;//实际白色背景top值
    private View mAnchorView;//锚点view

    public BgLinearLayout(Context context) {
        this(context, null);
    }

    public BgLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BgLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BgLinearLayout);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.BgLinearLayout_bgColor:
                    mBgColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.BgLinearLayout_bgHeightPercent:
                    mBgHeightPercent = a.getFloat(attr, 0F);
                    break;
                case R.styleable.BgLinearLayout_bgHeight:
                    float bgHeight = a.getDimension(attr, 0F);
                    mBgHeight = dpToPx(bgHeight);
                    break;
            }
        }
        a.recycle();
        checkBgHeightPercent();
        init();
    }

    /**
     * 检查mBgHeightPercent合理性,防止过度绘制
     */
    private void checkBgHeightPercent() {
        if (mBgHeightPercent > 1) {
            mBgHeightPercent = 1;
        }
        if (mBgHeightPercent < 0) {
            mBgHeightPercent = 0;
        }
    }

    private void init() {
        mPaint.setColor(mBgColor);
        mPaint.setDither(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawBg(canvas);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView != null) {
                Object tag = childView.getTag();
                if (tag != null && tag.equals(ANCHOR_TAG)) {
                    final int halfChildViewHeight = childView.getHeight() / 2;
                    final int top = childView.getTop();
                    mRealTop = halfChildViewHeight + top;
                    break;
                }
            }
        }
        mBgRectF.left = l;
        if (mRealTop != 0) {
            mBgRectF.top = mRealTop;
        } else if (mBgHeight != 0) {
            mBgRectF.top = getHeight() - mBgHeight;
        } else {
            mBgRectF.top = getHeight() * (1 - mBgHeightPercent);
        }
        mBgRectF.right = r;
        mBgRectF.bottom = b;
    }


    /**
     * 绘制背景,只绘制一个颜色即可,由下向上绘制
     */
    private void drawBg(Canvas canvas) {
        canvas.drawRect(mBgRectF, mPaint);
    }

    /**
     * 设置bg颜色
     */
    public void setBgColor(@ColorInt int color) {
        mBgColor = color;
    }

    /**
     * 设置bg高度百分比
     */
    public void setBgHeightPercent(float bgHeightPercent) {
        mBgHeightPercent = bgHeightPercent;
        checkBgHeightPercent();
        requestLayout();
    }

    /**
     * 设置bg高度
     *
     * @param bgHeight 单位dp
     */
    public void setBgHeight(float bgHeight) {
        mBgHeight = bgHeight;
        requestLayout();
    }

    /**
     * 设置锚点view/viewGroup，view/viewGroup必须是BgLinearLayout直接子类
     */
    public void setAnchorView(View anchorView) {
        mAnchorView = anchorView;
        if (anchorView == null) {
            return;
        }
        anchorView.setTag(ANCHOR_TAG);
    }

    /**
     * 获取锚点view，没有锚点view返回null
     */
    public View getAnchorView() {
        return mAnchorView;
    }

    private float dpToPx(float dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (dp * scale) + 0.5f;
    }

}
