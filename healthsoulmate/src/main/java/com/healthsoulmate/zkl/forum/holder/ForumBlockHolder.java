package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderForumBlockBinding;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/4/28.
 * 论坛板块内容条目
 */
public class ForumBlockHolder extends AppBaseHolder<PostsPageResponse.PageBean.RowsBean> {

    private HolderForumBlockBinding mBinding;

    public ForumBlockHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_forum_block;
    }

    @Override
    public void refreshView(int position) {
        PostsPageResponse.PageBean.RowsBean data = getData();
        mBinding.setItemInfo(data);
        mBinding.tvNum.setText(mActivity.getString(R.string.format_read_num, data.getReplyNum(), data.getReadNum()));
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderForumBlockBinding) viewDataBinding;
    }
}
