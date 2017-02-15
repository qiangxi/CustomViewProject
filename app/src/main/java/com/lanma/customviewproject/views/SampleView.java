package com.lanma.customviewproject.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lanma.customviewproject.utils.ScreenUtils;

/**
 * 作者 任强强 on 2017/2/7 09:56.
 * 该view非常简单
 * 主要用来学习:matrix(矩阵),shader(着色器),Xfermode,Camera,canvas等
 */

public class SampleView extends View {
    private String TAG = SampleView.class.getSimpleName();
    private Paint mPaint;
    private Matrix mMatrix;
    private Rect mTextRect;
    private String text = "这是文本";
    private Path mPath;

    public SampleView(Context context) {
        this(context, null);
    }

    public SampleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    private void initConfig() {
        mTextRect = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTextSize(ScreenUtils.spToPx(getContext(), 18));
        mPaint.getTextBounds(text, 0, text.length(), mTextRect);
        mMatrix = new Matrix();
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.moveTo(0, getHeight() / 2);
        mPath.quadTo(getWidth() / 4, getHeight() / 3, getWidth() / 2, getHeight() / 2);
        mPath.quadTo(getWidth() * 3 / 4, getHeight() * 2 / 3, getWidth(), getHeight() / 2);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 获取自身设置的leftMargin
     */
    private int getLeftMargin() {
        return ((ViewGroup.MarginLayoutParams) getLayoutParams()).leftMargin;
    }

    /**
     * 获取自身设置的topMargin
     */
    private int getTopMargin() {
        return ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin;
    }

    /**
     * 获取父View的paddingLeft
     */
    private int getVGPaddingLeft() {
        return ((ViewGroup) getParent()).getPaddingLeft();
    }

    /**
     * 获取父View的paddingTop
     */
    private int getVGPaddingTop() {
        return ((ViewGroup) getParent()).getPaddingTop();
    }

    /**
     * 获取文本的宽度(精确测量)
     */
    private int getTextWidth() {
        return (int) mPaint.measureText(text);
    }

    /**
     * 获取水平方向文本绘制起点(精确测量)
     */
    private int getTextStartX() {
        return (getWidth() - getTextWidth()) / 2;
    }

    /**
     * 获取文本高度(精确测量)
     */
    private int getTextHeight() {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        return fm.descent - fm.ascent;
    }

    /**
     * 获取垂直方向文本的绘制起点(方式一)
     * @param type 0:和TextView的计算方式一样,考虑到有音标的文本,
     *             1:适合只输入汉字或数字的文本(推荐)
     */
    private int getTextStartY(int type) {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        if (type == 0) {
            return getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        } else if (type == 1) {
            return getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        } else {
            throw new IllegalArgumentException("type只能是0或1");
        }
    }

}
