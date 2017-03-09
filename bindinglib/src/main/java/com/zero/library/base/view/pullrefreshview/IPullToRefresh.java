package com.zero.library.base.view.pullrefreshview;

import android.view.View;

import com.zero.library.base.view.pullrefreshview.PullToRefreshBase.OnRefreshListener;

interface IPullToRefresh<T extends View> {

    void setPullRefreshEnabled(boolean pullRefreshEnabled);

    void setPullLoadEnabled(boolean pullLoadEnabled);

    void setScrollLoadEnabled(boolean scrollLoadEnabled);

    boolean isPullRefreshEnabled();

    boolean isPullLoadEnabled();

    boolean isScrollLoadEnabled();

    void setOnRefreshListener(OnRefreshListener<T> refreshListener);

    void onPullDownRefreshComplete();

    void onPullUpRefreshComplete();

    T getRefreshableView();

    LoadingLayout getHeaderLoadingLayout();

    LoadingLayout getFooterLoadingLayout();

    void setLastUpdatedLabel(CharSequence label);
}
