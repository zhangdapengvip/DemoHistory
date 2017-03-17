package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderWaitPayOrderBinding;
import com.healthsoulmate.zkl.databinding.ItemImageviewBinding;
import com.healthsoulmate.zkl.ui.activity.AreadyPayOrderActivity;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.SettleAccountsRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.OredrhPageResponse;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;
import com.zero.library.base.view.AppProgressDialog;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/26.
 * 待付款列表
 */
public class WaitPayOrderHolder extends AppBaseHolder<OredrhPageResponse.ListBean> {

    private HolderWaitPayOrderBinding mBinding;
    private AppProgressDialog mProgressDialog;

    public WaitPayOrderHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_wait_pay_order;
    }

    @Override
    public void refreshView(int position) {
        OredrhPageResponse.ListBean data = getData();
        int count = 0;
        int payMoney = 0;

        List<OredrhPageResponse.ListBean.OrderbsBean> orderbs = data.getOrderbs();
        if (orderbs.size() == 1 && orderbs.get(0).getGoods().size() == 1) {
            mBinding.llOneImg.setVisibility(View.VISIBLE);
            mBinding.llImgs.setVisibility(View.GONE);
            OredrhPageResponse.ListBean.OrderbsBean.GoodsBean goodsBean = orderbs.get(0).getGoods().get(0);
            mBinding.setImgInfo(new ImgBinding(goodsBean.getImageurl(), R.drawable.ic_default_img,
                    R.dimen.dimen_60, 10));
            count += goodsBean.getBuynum();
            payMoney += goodsBean.getPrice();
            mBinding.tvTitle.setText(goodsBean.getGoodsname());
        } else {
            mBinding.llOneImg.setVisibility(View.GONE);
            mBinding.llImgs.setVisibility(View.VISIBLE);
            mBinding.llImgs.removeAllViews();
            for (OredrhPageResponse.ListBean.OrderbsBean orderb : orderbs) {
                for (OredrhPageResponse.ListBean.OrderbsBean.GoodsBean goodsItem : orderb.getGoods()) {
                    count += goodsItem.getBuynum();
                    payMoney += goodsItem.getPrice();
                    ItemImageviewBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                            R.layout.item_imageview, null, false);
                    inflate.setImgInfo(new ImgBinding(goodsItem.getImageurl(), R.drawable.ic_default_img,
                            R.dimen.dimen_60, 10));
                    mBinding.llImgs.addView(inflate.getRoot());
                }
            }
        }
        mBinding.tvCount.setText(mActivity.getString(R.string.format_product_count, count));
        mBinding.tvPrice.setText(mActivity.getString(R.string.format_real_pay, payMoney));
        mBinding.btnDopay.setOnClickListener(v -> doPay());

    }

    private void doPay() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        SettleAccountsRequest request = new SettleAccountsRequest();
        request.setPkBuyer(loginInfo.getDatas().getPkUser());
        request.setOrderNo(getData().getOrderno());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).settleAccountsRequest(request);
        mProgressDialog = new AppProgressDialog(mActivity);
        RetrofitUtils.request(mActivity, ob, mBinding.btnDopay,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        mProgressDialog.show();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, AreadyPayOrderActivity.class));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        mProgressDialog.dismiss();
                    }
                });
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderWaitPayOrderBinding) viewDataBinding;
    }
}
