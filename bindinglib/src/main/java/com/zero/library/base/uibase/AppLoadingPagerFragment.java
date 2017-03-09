package com.zero.library.base.uibase;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.UtilsView;
import com.zero.library.base.view.LoadingPage;
import com.zero.library.base.view.LoadingPage.LoadResult;

public abstract class AppLoadingPagerFragment extends AppBaseFragment {

    public AppLoadingPagerFragment() {
        super();
    }

    @Override
    protected View getContentView(final LayoutInflater inflater, final ViewGroup container) {
        if (null == mContentView) {
            mContentView = new LoadingPage(UtilsUi.getContext()) {
                @Override
                public void load() {
                    AppLoadingPagerFragment.this.load();
                }

                @Override
                public View createLoadedView() {
                    return AppLoadingPagerFragment.this.createLoadedView(inflater,container);
                }
            };
        } else {
            UtilsView.removeSelfFromParent(mContentView);
        }
        return mContentView;
    }

    public void show(LoadResult result) {
        if (mContentView != null) {
            ((LoadingPage) mContentView).setState(result.getValue());
            ((LoadingPage) mContentView).showPageSafe();
        }
    }

    public void onSelected() {
    }


    /**
     * 返回对应Tab的标题信息
     *
     * @return 标题
     */
    public abstract String getPagerTitle();

    /**
     * 加载页面数据,结束后调用{@link #show(LoadResult)}
     *
     * @see LoadResult
     */
    protected abstract void load();

    /**
     * 创建加载成功后显示界面
     *
     * @return 显示界面
     */
    protected View createLoadedView(LayoutInflater inflater, ViewGroup container) {
        ViewDataBinding inflate = DataBindingUtil.inflate(inflater, getResLayout(), container, false);
        View view = inflate.getRoot();
        initView(view, inflate);
        initData();
        return view;
    }

    /**
     * 保存数据
     *
     * @param key   key
     * @param value value
     */
    protected void saveInfo(String key, String value) {
        UtilsSharedPreference.setStringValue(key, value);
    }

    /**
     * 获取数据
     *
     * @param key key
     * @return 保存内容
     */
    protected String getSaveInfo(String key) {
        return UtilsSharedPreference.getStringValue(key);
    }
}
