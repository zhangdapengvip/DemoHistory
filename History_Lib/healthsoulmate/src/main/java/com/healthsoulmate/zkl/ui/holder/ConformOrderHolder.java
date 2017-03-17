package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderConformOrderBinding;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;

/**
 * Created by ZeroAries on 2016/5/17.
 * 确认订单
 */
public class ConformOrderHolder extends AppBaseHolder<ProductBean> {

    private HolderConformOrderBinding mBinding;

    public ConformOrderHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_conform_order;
    }

    @Override
    public void refreshView(int position) {
        ProductBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getImageurl(), R.drawable.ic_default_head, R.dimen.dimen_65));
        mBinding.tvTitle.setText(data.getGoodsname());
        mBinding.tvPrice.setText(mActivity.getString(R.string.format_price, data.getPrice()));
        mBinding.tvCount.setText(String.valueOf(data.getBuynum()));
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderConformOrderBinding) viewDataBinding;
    }
}