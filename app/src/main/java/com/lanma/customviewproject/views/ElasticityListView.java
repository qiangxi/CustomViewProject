package com.lanma.customviewproject.views;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 作者 任强强 on 2017/1/19 15:58.
 * 越界回弹ListView
 */

public class ElasticityListView extends ListView {
    private int downY;
    private int upY;
    private int initTop;
    private Scroller mScroller;

    public ElasticityListView(Context context) {
        this(context, null);
    }

    public ElasticityListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticityListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context, new DecelerateInterpolator());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initTop = getTop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int v = (int) (event.getY() - downY);
                ViewCompat.offsetTopAndBottom(this, v);
                break;
            case MotionEvent.ACTION_UP:
                upY = (int) event.getY();
//                mScroller.startScroll(getLeft(), getTop(), 0, -initTop + getTop(), 3000);
//                postInvalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ViewCompat.offsetTopAndBottom(this, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
