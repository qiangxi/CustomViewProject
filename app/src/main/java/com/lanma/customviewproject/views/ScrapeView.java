package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.utils.ImageUtils;


/**
 * 作者 任强强 on 2017/2/10 13:33.
 * 刮刮卡
 * 抽取出画笔宽度,背景图片或文字,前景图片,刮开百分比,刮开回调,前景样式(绘制方式,非图片),
 */

public class ScrapeView extends View {
    //常量
    //ScrapeView风格
    public static final int IMAGE = 0;
    public static final int TEXT = 1;

    private Paint mPaint;
    private Bitmap mForegroundBitmap;//前景图片(遮罩层图片)
    private Bitmap mDstBitmap;//背景图片
    private String mDstText;//背景文本
    private Rect mDstRect;
    private PorterDuffXfermode porterDuffXfermode;
    private Path mPath;//用户手指绘制的路径
    private float mPaintWidth = dpToPx(15);//画笔宽度
    private float mScrapeSuccessPercent = 0.4F;//刮开成功百分比,默认刮开40%时算刮开成功
    private int mScrapeStyle = 1;//风格:0=图片/1=文本
    private int mDstTextSize = spToPx(20);//背景文本大小
    private static final int[] ScrapeStyle = {IMAGE, TEXT};
    private int mTouchSlop;

    public ScrapeView(Context context) {
        this(context, null);
    }

    public ScrapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrapeView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ScrapeView_backgroundBitmap:
                    mDstBitmap = adjustDrawableToBitmap(a.getDrawable(attr));
                    break;
                case R.styleable.ScrapeView_foregroundBitmap:
                    mForegroundBitmap = adjustDrawableToBitmap(a.getDrawable(attr));
                    break;
                case R.styleable.ScrapeView_backgroundText:
                    mDstText = a.getString(attr);
                    break;
                case R.styleable.ScrapeView_backgroundTextSize:
                    mDstTextSize = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.ScrapeView_paintWidth:
                    mPaintWidth = a.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.ScrapeView_scrapeStyle:
                    mScrapeStyle = ScrapeStyle[a.getInt(R.styleable.ScrapeView_scrapeStyle, 0)];
                    break;
                case R.styleable.ScrapeView_successPercent:
                    mScrapeSuccessPercent = a.getFloat(attr, 0);
                    break;
            }
        }
        a.recycle();
        initConfig();
    }

    private Bitmap adjustDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        return null;
    }

    private void initConfig() {
        mPath = new Path();
        mDstRect = new Rect();
        mPaint = new Paint();
        //设置两个图层重合处的显示方式
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
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
            if (IMAGE == mScrapeStyle) {
                int[] bitmapSize = measureDstBitmapSize();
                totalWidth = Math.min(bitmapSize[0], widthSize);
            } else {
                int[] TextSize = measureDstTextSize();
                totalWidth = Math.min(TextSize[0], widthSize);
            }
        }
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            if (IMAGE == mScrapeStyle) {
                int[] bitmapSize = measureDstBitmapSize();
                totalHeight = Math.min(bitmapSize[1], heightSize);
            } else {
                int[] TextSize = measureDstTextSize();
                totalHeight = Math.min(TextSize[1], heightSize);
            }
        }
        setMeasuredDimension(totalWidth, totalHeight);
    }

    private int[] measureDstTextSize() {
        int[] totalTextSize = new int[2];
        if (TextUtils.isEmpty(mDstText)) {
            return totalTextSize;
        }
        totalTextSize[0] = getPaddingLeft() + getPaddingRight() + getTextWidth();
        totalTextSize[1] = getPaddingTop() + getPaddingBottom() + getTextHeight();
        return totalTextSize;
    }

    private int[] measureDstBitmapSize() {
        int[] totalBitmapSize = new int[2];
        if (!ImageUtils.checkBitmap(mDstBitmap)) {
            return totalBitmapSize;
        }
        totalBitmapSize[0] = getPaddingLeft() + getPaddingRight() + mDstBitmap.getWidth();
        totalBitmapSize[1] = getPaddingTop() + getPaddingBottom() + mDstBitmap.getHeight();
        return totalBitmapSize;
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//
//    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        invalidate();
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        //图片
        if (IMAGE == mScrapeStyle) {
            drawBackgroundBitmap(canvas);
        }
        //文本
        else {
            drawBackgroundText(canvas);
        }
        // 保存为单独的层
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint,
                Canvas.ALL_SAVE_FLAG);
        //绘制遮罩层
        drawForegroundBitmap(canvas);
        mPaint.setXfermode(porterDuffXfermode);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);//使用此种模式的画笔
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置下笔处和抬笔处以圆角方式绘制
        mPaint.setStrokeJoin(Paint.Join.ROUND);//设置连接处,拐弯处以圆角进行拟合
        canvas.drawPath(mPath, mPaint);//绘制用户手指滑动区域
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }

    private void drawBackgroundText(Canvas canvas) {
        if (TextUtils.isEmpty(mDstText)) {
            return;
        }
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(mDstTextSize);
        canvas.drawText(mDstText, getTextStartX(), getTextStartY(), mPaint);
    }

    private void drawBackgroundBitmap(Canvas canvas) {
        if (null == mDstBitmap) {
            return;
        }
        Bitmap bitmap = ImageUtils.scaleImg(mDstBitmap, getWidth(), getHeight());
        if (null == bitmap) {
            return;
        }
        mDstRect.left = 0;
        mDstRect.top = 0;
        mDstRect.right = getWidth();
        mDstRect.bottom = getHeight();
        canvas.drawBitmap(bitmap, null, mDstRect, mPaint);
    }

    private void drawForegroundBitmap(Canvas canvas) {
        if (null == mForegroundBitmap) {
            canvas.drawColor(Color.GRAY);
        } else {
            Bitmap bitmap = ImageUtils.scaleImg(mForegroundBitmap, getWidth(), getHeight());
            if (null == bitmap) {
                canvas.drawColor(Color.GRAY);
            } else {
                mDstRect.left = 0;
                mDstRect.top = 0;
                mDstRect.right = getWidth();
                mDstRect.bottom = getHeight();
                canvas.drawBitmap(bitmap, null, mDstRect, mPaint);
            }
        }
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX() - mLastX) > mTouchSlop || Math.abs(event.getY() - mLastY) > mTouchSlop) {
                    mPath.lineTo(event.getX(), event.getY());
                }
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //验证是否刮开到一定程度
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 计算刮掉的实时百分比
     */
    private void calculateCurrentPercent() {

    }

    /**
     * 设置前景图片(遮罩层图片)
     * @param srcBitmap 前景图片
     */
    public void setForegroundBitmap(Bitmap srcBitmap) {
        mForegroundBitmap = srcBitmap;
        invalidate();
    }

    public Bitmap getForegroundBitmap() {
        return mForegroundBitmap;
    }

    /**
     * 设置背景图片
     * @param dstBitmap 背景图片
     */
    public void setBackgroundBitmap(Bitmap dstBitmap) {
        mDstBitmap = dstBitmap;
        invalidate();
    }

    public Bitmap getBackgroundBitmap() {
        return mDstBitmap;
    }


    /**
     * 设置擦除线的宽度
     * @param paintWidth 单位dp,默认15dp
     */
    public void setPaintWidth(float paintWidth) {
        mPaintWidth = paintWidth;
        invalidate();
    }

    public float getPaintWidth() {
        return mPaintWidth;
    }

    /**
     * 设置刮开成功百分比
     * @param scrapeSuccessPercent 默认0.4F
     */
    public void setScrapeSuccessPercent(float scrapeSuccessPercent) {
        mScrapeSuccessPercent = scrapeSuccessPercent;
        invalidate();
    }

    public float getScrapeSuccessPercent() {
        return mScrapeSuccessPercent;
    }

    /**
     * 设置背景文本
     */
    public void setBackgroundText(String dstText) {
        mDstText = dstText;
        invalidate();
    }

    public String getBackgroundText() {
        return mDstText;
    }

    /**
     * 设置背景文本大小
     * @param dstTextSize 单位sp,默认20sp
     */
    public void setBackgroundTextSize(int dstTextSize) {
        mDstTextSize = dstTextSize;
        invalidate();
    }

    public int getBackgroundTextSize() {
        return mDstTextSize;
    }

    /**
     * 设置背景风格
     * @param scrapeStyle ScrapeView.IMAGE(默认) / ScrapeView.TEXT
     */
    public void setScrapeStyle(int scrapeStyle) {
        if (mScrapeStyle != scrapeStyle) {
            mScrapeStyle = scrapeStyle;
            invalidate();
        }
    }

    public int getScrapeStyle() {
        return mScrapeStyle;
    }

    public interface ScrapeCallback {
        /**
         * 当前已刮开的百分比
         */
        void onScraping(float currentPercent);

        /**
         * 刮开成功,当背景是文本时,text有值
         */
        void onScrapeSuccess(String text);
    }

    /**
     * ScrapeCallback的实现
     */
    public static class SampleScrapeCallback implements ScrapeCallback {

        @Override
        public void onScraping(float currentPercent) {
        }

        @Override
        public void onScrapeSuccess(String text) {
        }
    }

    private int dpToPx(int dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getContext().getResources().getDisplayMetrics());
    }

    private int getTextWidth() {
        return (int) mPaint.measureText(mDstText);
    }

    private int getTextHeight() {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        return fm.descent - fm.ascent;
    }

    /**
     * 获取水平方向文本绘制起点(精确测量)
     */
    private int getTextStartX() {
        return (getWidth() - getTextWidth()) / 2;
    }

    /**
     * 获取垂直方向文本的绘制起点(精确测量)
     */
    private int getTextStartY() {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        return getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
    }
}
