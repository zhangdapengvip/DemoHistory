package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderForumHistoryBinding;
import com.healthsoulmate.zkl.databinding.HolderPostHistoryBinding;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/5/26.
 * 帖子收藏列表
 */
public class ForumHistoryHolder extends AppBaseHolder<PostsPageResponse.PageBean.RowsBean> {

    private HolderForumHistoryBinding mBinding;

    public ForumHistoryHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_forum_history;
    }

    @Override
    public void refreshView(int position) {
        PostsPageResponse.PageBean.RowsBean data = getData();
        mBinding.setItemInfo(data);

    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderForumHistoryBinding) viewDataBinding;
    }
}
