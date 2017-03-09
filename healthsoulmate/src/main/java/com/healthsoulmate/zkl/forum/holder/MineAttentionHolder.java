package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderMineAttentionBinding;
import com.healthsoulmate.zkl.forum.protocol.response.UserfocusPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;

/**
 * Created by ZeroAries on 2016/6/2.
 * 我的关注列表
 */
public class MineAttentionHolder extends AppBaseHolder<UserfocusPageResponse.PageBean.RowsBean> {

    private HolderMineAttentionBinding mBinding;

    public MineAttentionHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_mine_attention;
    }

    @Override
    public void refreshView(int position) {
        UserfocusPageResponse.PageBean.RowsBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getUserbefocusImage(), R.drawable.ic_default_head,
                R.dimen.dimen_45, 10));
        mBinding.tvName.setText(data.getUserbefocusName());
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderMineAttentionBinding) viewDataBinding;
    }
}
