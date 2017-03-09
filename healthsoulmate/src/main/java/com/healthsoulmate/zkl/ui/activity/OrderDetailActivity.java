package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityOrderDetailBinding;
import com.healthsoulmate.zkl.ui.holder.OrderDetailHolder;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.SettleAccountsRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.OredrhPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsListView;
import com.zero.library.base.utils.UtilsString;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/17.
 * 订单详情
 */
public class OrderDetailActivity extends AppBaseActivity {

    private ActivityOrderDetailBinding mBinding;
    private DefaultAdapter<OredrhPageResponse.ListBean.OrderbsBean.GoodsBean> mAdapter;
    private OredrhPageResponse.ListBean mOrderInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityOrderDetailBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_order_detail);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mAdapter = new DefaultAdapter<OredrhPageResponse.ListBean.OrderbsBean.GoodsBean>(mActivity, mBinding.lvOrder) {
            @Override
            protected AppBaseHolder getHolder() {
                return new OrderDetailHolder(mActivity);
            }
        };
        mBinding.lvOrder.setAdapter(mAdapter);
        mBinding.btnDopay.setOnClickListener(v -> doPay());
    }

    private void doPay() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        SettleAccountsRequest request = new SettleAccountsRequest();
        request.setPkBuyer(loginInfo.getDatas().getPkUser());
        request.setOrderNo(mOrderInfo.getOrderno());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).settleAccountsRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnDopay,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
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
                        hidenProgressDialog();
                    }
                });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mOrderInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY);
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
        if (null == mOrderInfo || null == loginInfo) JumpManager.doJumpBack(mActivity);
        List<OredrhPageResponse.ListBean.OrderbsBean.GoodsBean> list = mOrderInfo.getOrderbs().get(0).getGoods();
        mAdapter.refreshData(list);
        UtilsListView.setListViewHeightBasedOnChildren(mBinding.lvOrder);

        mBinding.tvOrderNumber.setText(getString(R.string.format_order_number, mOrderInfo.getOrderno()));
        mBinding.tvTotlePrice.setText(getString(R.string.format_price, mOrderInfo.getFee()));
        mBinding.tvTotlePay.setText(getString(R.string.format_price, mOrderInfo.getActualfee()));
        mBinding.tvOrderTime.setText(getString(R.string.format_order_time, mOrderInfo.getOrdertime()));
        mBinding.tvBuyerName.setText(loginInfo.getDatas().getName());
        mBinding.tvBuyerPhone.setText(UtilsString.getPhoneNum(loginInfo.getDatas().getPhone()));
    }
}
