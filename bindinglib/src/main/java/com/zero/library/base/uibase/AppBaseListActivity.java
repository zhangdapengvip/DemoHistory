package com.zero.library.base.uibase;

import android.databinding.ViewDataBinding;
import android.widget.ListView;

import com.zero.library.R;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.view.pullrefreshview.PullToRefreshBase;
import com.zero.library.base.view.pullrefreshview.PullToRefreshListView;

/**
 * Created by ZeroAries on 2016/3/22.
 * 列表基类
 */
public abstract class AppBaseListActivity extends AppBaseActivity {
    protected DefaultAdapter mAdapter;
    protected PullToRefreshListView mRefreshList;

    @Override
    public int getResLayout() {
        return R.layout.include_pull_refresh;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mRefreshList = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
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
        ListView mListView =  mRefreshList.getRefreshableView();
        if (mListView.getHeaderViewsCount() == 0) addHeaderView(mListView);
        mAdapter = getAdapter(mListView);
        mListView.setAdapter(mAdapter);
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
     * 用于ListView添加头部信息 在{@link AppBaseActivity#initView(ViewDataBinding)}中调用
     *
     * @param listView
     */
    protected void addHeaderView(ListView listView) {
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
}
