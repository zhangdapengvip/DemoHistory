package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderHomeListBinding;
import com.healthsoulmate.zkl.ui.protocol.response.NewsBean;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;

/**
 * Created by ZeroAries on 2016/4/21.
 * 首页列表
 */
public class HomeListHolder extends AppBaseHolder<NewsBean> {

    private HolderHomeListBinding mBinding;

    public HomeListHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_home_list;
    }

    @Override
    public void refreshView(int position) {
        NewsBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getPictureurl(), R.drawable.ic_default_head, R.dimen.dimen_90));
        mBinding.setDataInfo(data);
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderHomeListBinding) viewDataBinding;
    }
}
