package com.lanma.customviewproject.views;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作者 任强强 on 2017/1/15 16:07.
 * 越界回弹ScrollView
 */

public class ElasticityScrollView extends ScrollView {
    private View mainView;
    private int initLeft;
    private int initTop;
    private ViewDragHelper mViewDragHelper;

    public ElasticityScrollView(Context context) {
        this(context, null);
    }

    public ElasticityScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticityScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ElasticityDragCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainView = getChildAt(0);
        initLeft = mainView.getLeft();
        initTop = mainView.getTop();
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

    private class ElasticityDragCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //从顶部往下拉,松开回到顶部
            if (mainView.getTop() > getTop()) {
                mViewDragHelper.settleCapturedViewAt(initLeft, initTop);
                postInvalidate();
            }
            //从底部往上拉,松开回到底部
            else if (mainView.getBottom() < getBottom()) {
                mViewDragHelper.settleCapturedViewAt(initLeft, initTop - (mainView.getHeight() - getHeight()));
                postInvalidate();
            }
            //fling
//            else {
//                int height = getHeight() - getPaddingBottom() - getPaddingTop();
//                int bottom = mainView.getHeight();
//                mViewDragHelper.flingCapturedView(0, getScrollY(), 0, Math.max(0, bottom - height));
//                postInvalidate();
//            }
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getHeight() / 2;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }
}
