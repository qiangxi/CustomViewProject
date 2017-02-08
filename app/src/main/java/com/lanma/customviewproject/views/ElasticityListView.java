package com.lanma.customviewproject.views;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * 作者 任强强 on 2017/1/19 15:58.
 * 越界回弹ListView
 */

public class ElasticityListView extends ListView {
    private int downY;
    private int initTop;
    private ElasticityListView mElasticityListView;
    private int mTouchSlop;
    private int newMoveY;

    public ElasticityListView(Context context) {
        this(context, null);
    }

    public ElasticityListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticityListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mElasticityListView = this;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return ScrollingUtil.isAbsListViewToBottom(this) || ScrollingUtil.isAbsListViewToTop(this);
//    }

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
//                ViewCompat.animate(this).translationY(initTop - getTop()).setListener(new ViewPropertyAnimatorListener() {
//                    @Override
//                    public void onAnimationStart(View view) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(View view) {
//                        ViewCompat.setTranslationY(mElasticityListView, initTop);
//                        requestLayout();
//                    }
//
//                    @Override
//                    public void onAnimationCancel(View view) {
//                    }
//                }).start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
