package com.lanma.customviewproject.views;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 作者 任强强 on 2017/1/14 11:47.
 * 仿qq侧滑删除布局
 */

public class SlideLayout extends LinearLayout {
    private String TAG = SlideLayout.class.getSimpleName();
    private int mTouchSlop;
    private float mVelocity;
    private View mainView;
    private View deleteView;
    private ViewDragHelper mViewDragHelper;
    private int initLeft;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //第二个参数的含义为滑动检测灵敏度,值越大,mTouchSlop越小,灵敏度越高
        mViewDragHelper = ViewDragHelper.create(this, 2.0f, new DragCallback());
        mTouchSlop = mViewDragHelper.getTouchSlop();
        mVelocity = mViewDragHelper.getMinVelocity();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new SecurityException("子view只能为2个");
        }
        mainView = getChildAt(0);//主内容layout
        initLeft = mainView.getLeft();
        deleteView = getChildAt(1);//侧滑删除layout
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mainView;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            deleteView.layout(changedView.getRight(), deleteView.getTop(), deleteView.getWidth() + changedView.getRight(), deleteView.getBottom());
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.e("tag", "xvel=" + xvel + ",yvel=" + yvel);
            //关闭
            if (Math.abs(Math.abs(releasedChild.getLeft()) - Math.abs(initLeft)) < deleteView.getWidth()) {
               closeMenu();
            }
            //打开
           else if (Math.abs(Math.abs(releasedChild.getLeft()) - Math.abs(initLeft)) >= deleteView.getWidth() / 2) {
                openMenu();
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return deleteView.getWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > 0) {
                left = 0;//只能从右往左滑
            } else if (Math.abs(left) > deleteView.getWidth()) {
                left = -deleteView.getWidth();//滑动的最大宽度为删除layout的宽度
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }

    /**
     * 打开删除layout
     */
    public void openMenu(){
        mViewDragHelper.settleCapturedViewAt(initLeft - deleteView.getWidth(), mainView.getTop());
        postInvalidate();
    }

    /**
     * 关闭删除layout
     */
    public void closeMenu(){
        mViewDragHelper.settleCapturedViewAt(initLeft, mainView.getTop());
        postInvalidate();
    }
}
