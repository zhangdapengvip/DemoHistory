package com.healthsoulmate.zkl.ui.activity;

import android.databinding.ViewDataBinding;
import android.widget.RadioGroup;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityMainTabBinding;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.bean.bus.CartBus;
import com.healthsoulmate.zkl.ui.bean.bus.LoginBus;
import com.healthsoulmate.zkl.ui.factory.FragmentFactory;
import com.healthsoulmate.zkl.ui.manager.ShoppingCartManager;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.PageShoppingGoodsResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.manager.FragmentManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseFragmentActivity;
import com.zero.library.base.utils.UtilsSharedPreference;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/21.
 * 首页
 */
public class MainTabActivity extends AppBaseFragmentActivity {

    private ActivityMainTabBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_main_tab;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        if (!UserManager.isAutoLogin()) UserManager.storeLoginInfo("");
        mBinding = (ActivityMainTabBinding) viewDataBinding;
        new FragmentManager(this, FragmentFactory.createHomeFragments(), R.id.tab_content, mBinding.tabsGroup) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                super.onCheckedChanged(radioGroup, checkedId);

            }
        };
    }

    @Override
    public void initData() {
        ShoppingCartManager.initFromLocal();
        registListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void registListener() {
        RxBus.getInstance().regist(CartBus.class).subscribe(cartBus -> {
                    UtilsSharedPreference.setIntValue(Constants.SHOPPING_COUNT, cartBus.getTotalNum());
                    ShoppingCartManager.notifyCount(mBinding.tvCartCount, cartBus.getTotalNum());
                }
        );
        RxBus.getInstance().regist(LoginBus.class).subscribe(login -> {
            LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
            if (null == loginInfo || ShoppingCartManager.productList.size() <= 0) return;
            ShoppingcartSaveRequest request = new ShoppingcartSaveRequest();
            request.setPkUser(loginInfo.getDatas().getPkUser());
            for (ProductBean productInfo : ShoppingCartManager.productList) {
                productInfo.setPkUser(loginInfo.getDatas().getPkUser());
            }
            request.setItems(ShoppingCartManager.productList);
            Observable<PageShoppingGoodsResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .shoppingcartSaveRequest(request);
            RetrofitUtils.request(mActivity, ob,
                    new RetrofitUtils.ResponseListener<PageShoppingGoodsResponse>() {
                        @Override
                        public void beforRequest() {
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(PageShoppingGoodsResponse response) {
                            RxBus.getInstance().send(new CartBus(response.getDatas().getCountNum()));
                            ShoppingCartManager.clearLocal();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onFinish(boolean isSuccess) {
                            hidenProgressDialog();
                        }
                    });
        });
    }
}
