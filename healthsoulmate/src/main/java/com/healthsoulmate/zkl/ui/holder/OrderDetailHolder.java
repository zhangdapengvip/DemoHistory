package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderOrderDetailBinding;
import com.healthsoulmate.zkl.ui.protocol.response.OredrhPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;

/**
 * Created by ZeroAries on 2016/5/17.
 * 订单详情条目
 */
public class OrderDetailHolder extends AppBaseHolder<OredrhPageResponse.ListBean.OrderbsBean.GoodsBean> {

    private HolderOrderDetailBinding mBinding;

    public OrderDetailHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_order_detail;
    }

    @Override
    public void refreshView(int position) {
        OredrhPageResponse.ListBean.OrderbsBean.GoodsBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getImageurl(), R.drawable.ic_default_img,
                R.dimen.dimen_60, 10));
        mBinding.tvTitle.setText(data.getGoodsname());
        mBinding.tvPrice.setText(mActivity.getString(R.string.format_price, data.getPrice()));
        mBinding.tvCount.setText(mActivity.getString(R.string.format_count, data.getBuynum()));
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderOrderDetailBinding) viewDataBinding;
    }
}
