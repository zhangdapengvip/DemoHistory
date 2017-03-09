package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderFindListBinding;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.binding.ImgBinding;

/**
 * Created by ZeroAries on 2016/4/21.
 * 发现列表
 */
public class FindListHolder extends AppBaseHolder<ProductBean> {

    private HolderFindListBinding mBinding;

    public FindListHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_find_list;
    }

    @Override
    public void refreshView(int position) {
        ProductBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getImageurl(), R.drawable.ic_default_head, R.dimen.dimen_80));

        mBinding.tvTitle.setText(data.getGoodsname());
        mBinding.tvCount.setText(mActivity.getString(R.string.format_evaluate_count, data.getCommentsnum()));
        mBinding.tvGoodRate.setText(mActivity.getString(R.string.format_good_rate, data.getReputably()) + "%");

        String price = mActivity.getString(R.string.format_price, UtilsString.formatPrice(Double.valueOf(data.getPrice())));
        CharSequence charSequence = UtilsString.textLadderString(price, ".", 1.0f, 0.8f);
        mBinding.tvPrice.setText(charSequence);
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderFindListBinding) viewDataBinding;
    }
}
