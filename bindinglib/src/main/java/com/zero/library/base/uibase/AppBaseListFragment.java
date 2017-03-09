package com.zero.library.base.uibase;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.ListView;

import com.zero.library.R;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.view.pullrefreshview.PullToRefreshBase;
import com.zero.library.base.view.pullrefreshview.PullToRefreshListView;

/**
 * Created by ZeroAries on 2016/3/14.
 * 列表基类
 */
public abstract class AppBaseListFragment extends AppPagerFragment {
    protected DefaultAdapter mAdapter;
    protected PullToRefreshListView mRefreshList;

    @Override
    protected int getResLayout() {
        return R.layout.include_pull_refresh;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mRefreshList = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ListView mListView = mRefreshList.getRefreshableView();
        if (mListView.getHeaderViewsCount() == 0) addHeaderView(mListView);
        if (mListView.getFooterViewsCount() == 0) addFooterView(mListView);
        mAdapter = getAdapter(mListView);
        mListView.setAdapter(mAdapter);
        mRefreshList.setPullRefreshEnabled(true);
        mRefreshList.setPullLoadEnabled(false);
        mRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onPullRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    /**
     * 下拉刷新结束
     */
    protected void pullDownComplete() {
        if (null != mRefreshList) {
            mRefreshList.setLastUpdatedLabel(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_DETAIL));
            mRefreshList.onPullDownRefreshComplete();
        }
    }

    /**
     * 用于ListView添加头部信息 在{@link AppBaseFragment#initView(View, ViewDataBinding)}中调用
     *
     * @param listView
     */
    protected void addHeaderView(ListView listView) {
    }

    /**
     * 用于ListView添加底部信息 在{@link AppBaseFragment#initView(View, ViewDataBinding)}中调用
     *
     * @param listView
     */
    protected void addFooterView(ListView listView) {
    }

    /**
     * 设置当前页面TAB标签
     *
     * @return 默认返回空
     */
    @Override
    public String getPagerTitle() {
        return "";
    }

    /**
     * 填充ListView的Adapter
     *
     * @param listView
     * @return
     */
    protected abstract DefaultAdapter getAdapter(ListView listView);

    /**
     * 下拉刷新时调用
     *
     * @return
     */
    protected abstract void onPullRefresh();

    @Override
    public void onDestroy() {
        super.onDestroy();
        pullDownComplete();
    }
}
