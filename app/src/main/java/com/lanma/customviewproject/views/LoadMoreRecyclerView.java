package com.lanma.customviewproject.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 只具有上拉加载的recycleView,下拉刷新使用Android自带的SwipeRefreshLayout
 * 还存在的问题: 1.加载数据时,item中的布局大小会出现问题
 *              2.当数据不足一屏时,会显示正在加载的LoadMoreView,正常情况下应该显示'数据已加载完毕'
 *
 *  所有的列表界面暂时不做,等完善LoadMoreRecyclerView之后或者找到合适的开源替代品之后再说
 * </p>
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private boolean isLoadMoreEnable;//是否开启上拉加载
    private boolean isLoadingData;//是否正在加载数据

    private onLoadMoreListener mLoadMoreListener;
    private LoadMoreView mLoadMoreView;
    private WrapAdapter mWrapAdapter;
    private DataObserver mDataObserver = new DataObserver();
    private boolean isShowLoadMoreView = true;//是否显示LoadMoreView
    private boolean isShowLoadMoreProgress = true;//是否显示LoadMoreView中的ProgressView
    private boolean isAllDataLoaded;//是否所有数据加载完毕

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLoadMoreView = new LoadMoreView(context);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mLoadMoreView.setLayoutParams(lp);
        mLoadMoreView.showLoadMoreView(isShowLoadMoreView);
        mLoadMoreView.showProgressView(isShowLoadMoreProgress);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (RecyclerView.SCROLL_STATE_IDLE == state && mLoadMoreListener != null
                && isLoadMoreEnable && !isLoadingData && !isAllDataLoaded) {
            final LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
            if (layoutManager.getChildCount() > 0) {
                final View lastVisibleItemView =
                        layoutManager.findViewByPosition(lastVisibleItemPosition);
                if (lastVisibleItemView != null) {
                    if (lastVisibleItemView.getBottom() == ((getBottom() - getPaddingBottom()))) {
                        isLoadingData = true;
                        mLoadMoreView.setLoadMoreState(LoadMoreView.State.LOADING);
                        mLoadMoreListener.onLoadMore();
                    }
                }
            }
        }
    }

    /**
     * 获取最后一个可见item的位置
     */
    private int getLastVisibleItemPosition(LayoutManager layoutManager) {
        int lastVisibleItemPosition;
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            final int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
            lastVisibleItemPosition = findMax(into);
        } else {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setLoadMoreEnable(boolean isLoadMoreEnable) {
        this.isLoadMoreEnable = isLoadMoreEnable;
    }

    public boolean isLoadMoreEnable() {
        return isLoadMoreEnable;
    }

    public void setLoadMoreListener(onLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public interface onLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 单次数据加载完成
     */
    public void onLoadMoreCompleted() {
        isLoadingData = false;
        mLoadMoreView.setLoadMoreState(LoadMoreView.State.COMPLETED);
    }


    public void onLoadMoreFailed() {
        onLoadMoreFailed("数据加载失败,请点击重试");
    }

    /**
     * 单次数据加载失败
     */
    public void onLoadMoreFailed(String loadMoreFailedText) {
        if (mLoadMoreView != null) {
            mLoadMoreView.setLoadFailedText(loadMoreFailedText);
            showLoadMoreProgressView(false);
            mLoadMoreView.setLoadMoreState(LoadMoreView.State.FAILED);
        }
    }

    public void onLoadMoreAllData() {
        onLoadedAllData("已加载所有数据");
    }

    /**
     * 所有数据已加载完毕
     */
    public void onLoadedAllData(String loadMoreAllDataText) {
        if (mLoadMoreView != null) {
            isAllDataLoaded = true;
            mLoadMoreView.setLoadedAllDataText(loadMoreAllDataText);
            showLoadMoreProgressView(false);
            mLoadMoreView.setLoadMoreState(LoadMoreView.State.LOAD_ALL);
        }
    }

    public void setLoadingText(String loadText) {
        if (mLoadMoreView != null) {
            mLoadMoreView.setLoadingText(loadText);
        }
    }

    public void showLoadMoreView(boolean isShow) {
        if (mLoadMoreView != null) {
            isShowLoadMoreView = isShow;
            mLoadMoreView.showLoadMoreView(isShow);
        }
    }

    private void showLoadMoreProgressView(boolean isShow) {
        if (mLoadMoreView != null) {
            isShowLoadMoreProgress = isShow;
            mLoadMoreView.showProgressView(isShow);
        }
    }

    /**
     * 当数据加载失败时点击重新加载数据
     */
    public void setOnDataLoadFailedListener(final LoadMoreView.DataLoadFailedClickListener listener) {
        if (mLoadMoreView != null) {
            mLoadMoreView.setOnDataLoadFailedListener(listener);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null) {
            return mWrapAdapter.getAdapter();
        }
        return null;
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    private class WrapAdapter extends Adapter<ViewHolder> {
        private static final int LOAD_MORE_VIEW_TYPE = 0x10000;
        private Adapter mRealAdapter;//实际的adapter

        WrapAdapter(Adapter adapter) {
            mRealAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mRealAdapter.getItemCount() && isLoadMoreEnable) {
                return LOAD_MORE_VIEW_TYPE;
            }
            return mRealAdapter.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //添加加载更多view
            if (viewType == LOAD_MORE_VIEW_TYPE) {
                return new LoadMoreViewHolder(mLoadMoreView);
            } else {
                return mRealAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position != mRealAdapter.getItemCount()) {
                mRealAdapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            //多加一个loadMoreView
            return mRealAdapter.getItemCount() + 1;
        }

        RecyclerView.Adapter getAdapter() {
            return mRealAdapter;
        }
    }

    private static class LoadMoreViewHolder extends ViewHolder {

        LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
