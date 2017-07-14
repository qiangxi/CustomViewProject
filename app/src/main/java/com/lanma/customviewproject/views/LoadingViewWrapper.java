package com.lanma.customviewproject.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/14 16:00
 */

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: CustomViewProject
 * @package: com.lanma.customviewproject.views
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/14 16:00
 * @description: <p>
 * <p>
 * </p>
 */

public class LoadingViewWrapper extends LinearLayout {
    private LoadingView mLoadingView;

    public LoadingViewWrapper(Context context) {
        this(context, null);
    }

    public LoadingViewWrapper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingViewWrapper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        LoadingView loadingView = new LoadingView(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingView.setLayoutParams(lp);
        addView(loadingView);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(loadingView, "rotation", 0F, 360F);
        rotation.setDuration(1000);
        rotation.setRepeatMode(ValueAnimator.RESTART);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
//        rotation.setStartDelay(200);
//        rotation.setInterpolator(new DecelerateInterpolator());
        rotation.start();
//        loadingView.animate().rotation(360).setDuration(1500).setInterpolator(new DecelerateInterpolator()).start();
    }
}
