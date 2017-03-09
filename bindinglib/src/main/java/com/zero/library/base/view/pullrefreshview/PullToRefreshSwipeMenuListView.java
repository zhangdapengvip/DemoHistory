package com.zero.library.base.view.pullrefreshview;

import android.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ExpandableListView;

import com.zero.library.base.view.pullrefreshview.ILoadingLayout.State;
import com.zero.library.base.view.swipemenulistview.SwipeMenuListView;

public class PullToRefreshSwipeMenuListView extends PullToRefreshBase<SwipeMenuListView>
        implements OnScrollListener {

    private SwipeMenuListView mListView;
    private LoadingLayout mLoadMoreFooterLayout;
    private OnScrollListener mScrollListener;

    public PullToRefreshSwipeMenuListView(Context context) {
        this(context, null);
    }

    public PullToRefreshSwipeMenuListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshSwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        setPullLoadEnabled(false);
    }

    @Override
    protected SwipeMenuListView createRefreshableView(Context context, AttributeSet attrs) {
        SwipeMenuListView listView = new SwipeMenuListView(context, attrs);
        listView.setId(R.id.list);
        mListView = listView;
        // mListView.setFooterDividersEnabled(true);
        mListView.setSelector(new BitmapDrawable());
        android.view.ViewGroup.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mListView.setLayoutParams(params);
        listView.setOnScrollListener(this);
        return listView;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        refreshLoadingViewsSize();
        refreshRefreshableViewSize(w, h);
        post(new Runnable() {
            @Override
            public void run() {
                int scrollX = getRefreshableView().getScrollX();
                int scrollY = getRefreshableView().getScrollY();
                requestLayout();
                getRefreshableView().scrollTo(scrollX, scrollY);
            }
        });
    }

    public void setHasMoreData(boolean hasMoreData) {
        if (!hasMoreData) {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.setState(State.NO_MORE_DATA);
            }

            LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
            if (null != footerLoadingLayout) {
                footerLoadingLayout.setState(State.NO_MORE_DATA);
            }
        }
    }

    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    protected boolean isReadyForPullUp() {
        return isLastItemVisible();
    }

    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    @Override
    protected void startLoading() {
        super.startLoading();

        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(State.REFRESHING);
        }
    }

    @Override
    public void onPullUpRefreshComplete() {
        super.onPullUpRefreshComplete();

        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(State.RESET);
        }
    }

    @Override
    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
        super.setScrollLoadEnabled(scrollLoadEnabled);

        if (scrollLoadEnabled) {
            if (null == mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout = new FooterLoadingLayout(getContext());
            }

            if (null == mLoadMoreFooterLayout.getParent()) {
                mListView.addFooterView(mLoadMoreFooterLayout, null, false);
            }
            mLoadMoreFooterLayout.show(true);
        } else {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.show(false);
            }
        }
    }

    @Override
    public LoadingLayout getFooterLoadingLayout() {
        if (isScrollLoadEnabled()) {
            return mLoadMoreFooterLayout;
        }

        return super.getFooterLoadingLayout();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isScrollLoadEnabled() && hasMoreData()) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    || scrollState == OnScrollListener.SCROLL_STATE_FLING) {
                if (isReadyForPullUp()) {
                    startLoading();
                }
            }
        }

        if (null != mScrollListener) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (null != mScrollListener) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    @Override
    protected LoadingLayout createHeaderLoadingLayout(Context context,
                                                      AttributeSet attrs) {
        return new HeaderLoadingLayout(context);
    }

    private boolean hasMoreData() {
        return !((null != mLoadMoreFooterLayout)
                && (mLoadMoreFooterLayout.getState() == State.NO_MORE_DATA));

    }

    private boolean isFirstItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        int mostTop = (mListView.getChildCount() > 0) ? mListView.getChildAt(0)
                .getTop() : 0;
        return mostTop >= 0;

    }

    private boolean isLastItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = mListView.getLastVisiblePosition();

        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition
                    - mListView.getFirstVisiblePosition();
            final int childCount = mListView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mListView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mListView.getBottom();
            }
        }
        return false;
    }
}
