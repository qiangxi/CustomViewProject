package com.lanma.customviewproject.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lanma.customviewproject.R;
import com.lanma.customviewproject.databinding.ItemLoadMoreLayoutBinding;

/**
 * 加载更多view
 *
 */

public class LoadMoreView extends LinearLayout {
    public enum State {

        LOADING(0), COMPLETED(1),
        FAILED(2), LOAD_ALL(3);

        State(int state) {
        }
    }


    private ItemLoadMoreLayoutBinding mBinding;
    private State mState = State.LOADING;
    private String loadedAllDataText;
    private String loadingDataText;
    private String loadedFailedText;
    private DataLoadFailedClickListener mLoadFailedClickListener;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
        initEvent();
    }

    private void initEvent() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == State.FAILED && mLoadFailedClickListener != null) {
                    if (TextUtils.isEmpty(loadingDataText)) {
                        setLoadingText("正在加载...");
                    } else {
                        setLoadingText(loadingDataText);
                    }
                    showProgressView(true);
                    mLoadFailedClickListener.dataLoadFailedClick();
                }
            }
        });
    }

    private void initLayout() {
        removeAllViews();
        LayoutInflater inflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_load_more_layout, null, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        View root = mBinding.getRoot();
        root.setLayoutParams(lp);
        addView(root);
    }

    public void setLoadingText(String loadingDataText) {
        this.loadingDataText = loadingDataText;
        mBinding.setLoadMoreText(loadingDataText);
    }

    public void setLoadedAllDataText(String loadedAllDataText) {
        this.loadedAllDataText = loadedAllDataText;
        mBinding.setLoadMoreText(loadedAllDataText);
    }

    public void setLoadFailedText(String loadedFailedText) {
        this.loadedFailedText = loadedFailedText;
        mBinding.setLoadMoreText(loadedFailedText);
    }

    public void showLoadMoreView(boolean isShow) {
        mBinding.setShowLoadMoreView(isShow);
    }

    public void showProgressView(boolean isShow) {
        mBinding.setShowProgress(isShow);
    }

    public void setOnDataLoadFailedListener(final DataLoadFailedClickListener listener) {
        mLoadFailedClickListener = listener;
    }

    public void setLoadMoreState(State loadMoreState) {
        mState = loadMoreState;
    }

    public interface DataLoadFailedClickListener {
        void dataLoadFailedClick();
    }
}
