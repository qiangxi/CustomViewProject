package com.lanma.customviewproject.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.lanma.customviewproject.R;


/**
 * 正六边形TextView
 *
 * @author 任强强
 * @since 2016.07.05
 */
public class HexagonTextView extends View {

    private Paint mTextPaint;//文字画笔
    private Paint mLinePaint;//线画笔
    private String mLeftTopText;//左上文字
    private String mRightBottomText;//右下文字
    private float mTextSize;//文字大小
    private int mTextColor;//文字颜色
    private int mLineColor;//线的颜色
    private Rect mBound;//文字尺寸
    private Path path;

    public HexagonTextView(Context context) {
        this(context, null);
    }

    public HexagonTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HexagonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HexagonTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.HexagonTextView_leftTopText:
                    mLeftTopText = a.getString(attr);
                    break;
                case R.styleable.HexagonTextView_rightBottomText:
                    mRightBottomText = a.getString(attr);
                    break;
                case R.styleable.HexagonTextView_lineColor:
                    mLineColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.HexagonTextView_textColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.HexagonTextView_textSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        init();
    }

    private void init() {
        //文字paint初始化
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mBound = new Rect();
        mTextPaint.getTextBounds(mLeftTopText, 0, mLeftTopText.length(), mBound);
        //线paint初始化
        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(2f);
        mLinePaint.setStyle(Paint.Style.STROKE);
        path = new Path();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        //计算宽度
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getSuggestedMinimumWidth();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(widthSize, width);
            }
        }
        //计算高度
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getSuggestedMinimumHeight();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(heightSize, height);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        //绘制六边形
        //上半部分
        path.moveTo(paddingLeft, width / 2 + paddingTop);//起始点
        path.lineTo(paddingLeft + height / 4, paddingTop);//左
        path.lineTo(3 * width / 4 + paddingLeft, paddingTop);//上
        path.lineTo(width + paddingLeft, height / 2 + paddingTop);//右
        //下半部分
        path.lineTo(3 * width / 4 + paddingLeft, height + paddingTop);//右
        path.lineTo(paddingLeft + width / 4, height + paddingTop);//下
        path.lineTo(paddingLeft, height / 2 + paddingTop);//左
        canvas.drawPath(path, mLinePaint);
        canvas.drawLine(paddingLeft + width / 4, height + paddingTop, 3 * width / 4 + paddingLeft, paddingTop, mLinePaint);//中间斜线
        //绘制左上文本
        canvas.drawText(mLeftTopText, paddingLeft + width / 3 - mBound.width() / 2, height / 2 - mBound.height() / 2 + paddingTop, mTextPaint);
        //绘制右下文本
        canvas.drawText(mRightBottomText, paddingLeft + 2 * width / 3 - mBound.width() / 2, 3 * height / 4 - mBound.height() / 2 + paddingTop, mTextPaint);
    }

    public void setLeftTopText(String leftTopText) {
        this.mLeftTopText = leftTopText;
        invalidate();
    }

    public void setRightBottomText(String rightBottomText) {
        this.mRightBottomText = rightBottomText;
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        invalidate();
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        invalidate();
    }
}
