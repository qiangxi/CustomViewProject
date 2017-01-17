package com.lanma.customviewproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.orhanobut.logger.Logger;

/**
 * 作者 任强强 on 2016/8/23 15:05.
 * touch事件练习
 */
public class TouchEventTest extends View {
    private Paint mPaint;
    private OnClickListener mListener;
    private onViewClick mViewClick;
    private int startX;//起始x坐标
    private int startY;//起始y坐标
    private Scroller mScroller;
    private int mTouchSlop;
    private int x = getWidth() / 2;
    private int y = getHeight() / 2;

    public TouchEventTest(Context context) {
        this(context, null);
    }

    public TouchEventTest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchEventTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mPaint = new Paint();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("view内部设置OnClickListener", "");
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       // rawTop = getTop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, 30, mPaint);
    }


    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
    }

    private int startRawX;
    private int startRawY;
    private int rawLeft;
    private int rawTop;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                startRawX = rawX;
                startRawY = rawY;
                rawLeft = getLeft();
                rawTop = getTop();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - startX;
                if (Math.abs(offsetX) >= mTouchSlop) {
                    layout(getLeft() + offsetX, getTop(), getRight() + offsetX, getBottom());
                    //scrollBy(-offsetX, -y + startY);
//                    smoothScrollTo(-getLeft() - offsetX,-getTop()-y-startY);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 开启动画
//                TranslateAnimation anim = new TranslateAnimation(rawX, startRawX, rawTop, rawTop);
//                anim.setDuration(1000);
//
//                startAnimation(anim);

//                if (Math.abs(rawX - startRawX) < 100) {
//                    smoothScrollTo(rawX - startRawX, rawX);
//                }
//                if (x + getLeft() < getRight() && y + getTop() < getBottom()) {
//                    mListener.onClick(this);
//                    mViewClick.onClick(rawX - startRawX, rawY - startRawY);
//                }
                break;
        }
        return true;
    }
//
//    private void smoothScrollTo(int destX, int rawX) {
//        int scrollX = getScrollX();
////        int delta = destX - scrollX;
//        mScroller.startScroll(rawX, getTop(), destX, 0, 5000);
//        invalidate();
//    }
//
//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        if (mScroller.computeScrollOffset()) {
//            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            invalidate();
//        }
//    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mListener = l;
    }

    public void setOnViewClick(onViewClick click) {
        this.mViewClick = click;
    }


    public interface onViewClick {
        /**
         * @param scrollX 从按下到抬起,X轴方向移动的距离
         * @param scrollY 从按下到抬起,Y轴方向移动的距离
         */
        void onClick(float scrollX, float scrollY);
    }
}
