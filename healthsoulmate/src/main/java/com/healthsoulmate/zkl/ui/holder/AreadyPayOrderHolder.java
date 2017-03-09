package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderAreadyPayOrderBinding;
import com.healthsoulmate.zkl.ui.activity.EvaluateOrderActivity;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;

/**
 * Created by ZeroAries on 2016/5/30.
 * 已付款
 */
public class AreadyPayOrderHolder extends AppBaseHolder<ProductBean> {

    private HolderAreadyPayOrderBinding mBinding;

    public AreadyPayOrderHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_aready_pay_order;
    }

    @Override
    public void refreshView(int position) {
        ProductBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getImageurl(), R.drawable.ic_default_img, R.dimen.dimen_70, 10));
        mBinding.tvTitle.setText(data.getGoodsname());
        mBinding.btnEvaluate.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, EvaluateOrderActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, data);
            JumpManager.doJumpForward(mActivity, intent);
        });
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderAreadyPayOrderBinding) viewDataBinding;
    }
}
