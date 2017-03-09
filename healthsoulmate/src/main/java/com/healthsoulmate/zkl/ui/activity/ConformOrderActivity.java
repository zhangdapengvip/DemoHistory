package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityConformOrderBinding;
import com.healthsoulmate.zkl.ui.holder.ConformOrderHolder;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.OredrhSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
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

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/27.
 * 确认订单
 */
public class ConformOrderActivity extends AppBaseActivity {

    private ActivityConformOrderBinding mBinding;
    private DefaultAdapter<ProductBean> mAdapter;
    private ArrayList<ProductBean> mProductList;

    @Override
    public int getResLayout() {
        return R.layout.activity_conform_order;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityConformOrderBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_conform_order);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));

        mAdapter = new DefaultAdapter<ProductBean>(mActivity, mBinding.lvProduct) {
            @Override
            protected AppBaseHolder getHolder() {
                return new ConformOrderHolder(mActivity);
            }
        };
        mBinding.lvProduct.setAdapter(mAdapter);
        mBinding.btnMakeOrder.setOnClickListener(v -> makeOrder());
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mProductList = intent.getParcelableArrayListExtra(AppConstants.PARCELABLE_KEY);
        mAdapter.refreshData(mProductList);
        UtilsListView.setListViewHeightBasedOnChildren(mBinding.lvProduct);

        Double price = 0.0;
        for (ProductBean product : mProductList) {
            price += Double.valueOf(product.getPrice());
        }
        String totalPrice = mActivity.getString(R.string.format_total_price, UtilsString.formatPrice(price));
        CharSequence charSequence = UtilsString.textLadderString(totalPrice, ".", 1.0f, 0.8f);
        mBinding.tvTotlePrice.setText(charSequence);
        mBinding.tvPrice.setText(mActivity.getString(R.string.format_price, UtilsString.formatPrice(price)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null != loginInfo) {
            mBinding.tvName.setText(loginInfo.getDatas().getName());
            mBinding.tvPhone.setText(UtilsString.getPhoneNum(loginInfo.getDatas().getPhone()));
        }
    }

    private void makeOrder() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        OredrhSaveRequest request = new OredrhSaveRequest();
        request.setPkBuyer(loginInfo.getDatas().getPkUser());
        for (ProductBean product : mProductList) {
            OredrhSaveRequest.OrderbsBean item = new OredrhSaveRequest.OrderbsBean();
            item.setNum(product.getBuynum());
            item.setPkGood(product.getPkGoods());
            request.getOrderbs().add(item);
        }
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).oredrhSaveRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnMakeOrder,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, WaitPayOrderActivity.class));
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
}
