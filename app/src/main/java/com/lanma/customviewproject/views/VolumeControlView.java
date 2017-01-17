package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.lanma.customviewproject.R;
import com.orhanobut.logger.Logger;

/**
 * 作者 qiang_xi on 2016/9/13 15:20.
 * 音量调节器view
 */
public class VolumeControlView extends View {

    private final int DefaultBlockCount = 10;//默认区块数量
    private final int DefaultBlankDistance = dpToPx(30);//内圆距外侧区块的默认距离
    private final int DefaultBlockRadius = dpToPx(5);//区块半径

    private Paint mTextPaint;//文本画笔
    private Paint mCirclePaint;//圆圈画笔
    private Paint mBlockPaint;//外侧区块画笔

    private String mCircleText;//圆圈内的文本
    private int mTextColor = Color.WHITE;//文本颜色
    private int mTextSize = spToPx(16);//文本字体大小

    private int mCircleColor = Color.WHITE;//圆圈颜色
    private int mCircleWidth = dpToPx(3);//圆圈宽度
    private int mCircleRadius = dpToPx(60);//圆圈半径

    private int mBlockColor = Color.WHITE;//区块颜色
    private int mBlockTotalCount = DefaultBlockCount;//区块总数量
    private int mCurrentBlockCount = 4;//当前选中的区块数量
    private RectF rectF;
    private int mTouchSlop;
    //最小滑动距离==1个圆圈
    private int minMoveDistance = (getScreenWidth() > getScreenHeight() ? getScreenHeight() : getScreenWidth()) / mBlockTotalCount;

    int oldY = 0;

    public VolumeControlView(Context context) {
        this(context, null);
    }

    public VolumeControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VolumeControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.VolumeControlView_circleTextSize:
                    mTextSize = a.getInt(attr, spToPx(16));
                    break;
                case R.styleable.VolumeControlView_circleTextColor:
                    mTextColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.VolumeControlView_CircleColor:
                    mCircleColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.VolumeControlView_circleRadius:
                    mCircleRadius = a.getColor(attr, dpToPx(60));
                    break;
                case R.styleable.VolumeControlView_circleWidth:
                    mCircleWidth = a.getInt(attr, dpToPx(3));
                    break;
                case R.styleable.VolumeControlView_blockColor:
                    mBlockColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.VolumeControlView_blockTotalCount:
                    mBlockTotalCount = a.getInt(attr, DefaultBlockCount);
                    break;
            }
        }
        a.recycle();
        init();
    }

    private void init() {
        //文本画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        //圆圈画笔
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(mCircleWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        //外侧区块画笔
        mBlockPaint = new Paint();
        mBlockPaint.setColor(mBlockColor);
        mBlockPaint.setAntiAlias(true);
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆圈
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCircleRadius, mCirclePaint);
        //绘制文字
        canvas.drawText(mCircleText, getWidth() / 2 - getTextWidth(mCircleText) / 2, getHeight() / 2 + getTextHeight(mCircleText) / 2, mTextPaint);
        //绘制区块
        //先绘制mBlockTotalCount个空白小圆圈
        canvas.save();
        for (int i = 0; i < mBlockTotalCount; i++) {
            if (i < mCurrentBlockCount) {
                mBlockPaint.setStyle(Paint.Style.FILL);
                rectF.left = getWidth() / 2 - mCircleRadius - DefaultBlankDistance;
                rectF.top = getHeight() / 2 - mCircleRadius - DefaultBlankDistance;
                rectF.right = getWidth() / 2 + mCircleRadius + DefaultBlankDistance;
                rectF.bottom = getHeight() / 2 + mCircleRadius + DefaultBlankDistance;
                canvas.drawArc(rectF, -180, 360 / mBlockTotalCount, false, mCirclePaint);
            } else {
                mBlockPaint.setStyle(Paint.Style.STROKE);
            }
            canvas.drawCircle(getWidth() / 2 - mCircleRadius - DefaultBlankDistance, getHeight() / 2, DefaultBlockRadius, mBlockPaint);
            canvas.rotate(360 / mBlockTotalCount, getWidth() / 2, getHeight() / 2);
        }
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touch = false;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getRawY() - oldY) > mTouchSlop) {
                    Logger.e("event.getRawY()=" + event.getRawY());
                    Logger.e("event.getY() - oldY=" + (event.getRawY() - oldY));
                    //向下滑动
                    if (event.getRawY() - oldY > 0) {

                        if (Math.abs(event.getY() - oldY)>minMoveDistance) {
                            reduceVolume();
                        }
                    }
                    //向上滑动
                    else {
                        if (Math.abs(event.getY() - oldY)>minMoveDistance) {
                            addVolume();
                        }
                    }
                }
                oldY = (int) event.getRawY();
                Logger.e("oldY=" + oldY);
                break;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void setCircleText(String text) {
        this.mCircleText = text;
        invalidate();
    }

    public String getCircleText() {
        return mCircleText;
    }

    public void setCircleTextColor(int textColor) {
        this.mTextColor = textColor;
        invalidate();
    }

    public int getCircleTextColor() {
        return mTextColor;
    }

    public void setCircleTextSize(int textSize) {
        this.mTextSize = textSize;
        invalidate();
    }

    public int getCircleTextSize() {
        return mTextSize;
    }

    public void setCircleColor(int circleColor) {
        this.mCircleColor = circleColor;
        invalidate();
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleRadius(int circleRadius) {
        this.mCircleRadius = circleRadius;
        invalidate();
    }

    public int getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleWidth(int circleWidth) {
        this.mCircleWidth = circleWidth;
        invalidate();
    }

    public int getCircleWidth() {
        return mCircleWidth;
    }

    public void setBlockColor(int blockColor) {
        this.mBlockColor = blockColor;
        invalidate();
    }

    public int getBlockColor() {
        return mBlockColor;
    }

    public void setBlockTotalCount(int blockTotalCount) {
        this.mBlockTotalCount = blockTotalCount;
        invalidate();
    }

    public int getBlockTotalCount() {
        return mBlockTotalCount;
    }

    public void addVolume() {
        mCurrentBlockCount++;
        if (mCurrentBlockCount > mBlockTotalCount) {
            mCurrentBlockCount = mBlockTotalCount;
        }
        invalidate();
    }

    public void reduceVolume() {
        mCurrentBlockCount--;
        if (mCurrentBlockCount < 0) {
            mCurrentBlockCount = 0;
        }
        invalidate();
    }

    public void setCurrentVolume(int currentBlockCount) {
        if (currentBlockCount >= mBlockTotalCount) {
            currentBlockCount = mBlockTotalCount - 1;
        } else if (currentBlockCount < 0) {
            currentBlockCount = 0;
        }
        this.mCurrentBlockCount = currentBlockCount;
        invalidate();
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

    private int getScreenHeight() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

}
