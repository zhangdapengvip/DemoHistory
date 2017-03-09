package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderPostHistoryBinding;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/5/26.
 * 我发布的帖子
 */
public class PostHistoryHolder extends AppBaseHolder<PostsPageResponse.PageBean.RowsBean> {

    private HolderPostHistoryBinding mBinding;

    public PostHistoryHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_post_history;
    }

    @Override
    public void refreshView(int position) {
        PostsPageResponse.PageBean.RowsBean data = getData();
        mBinding.setItemInfo(data);

    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderPostHistoryBinding) viewDataBinding;
    }
}
