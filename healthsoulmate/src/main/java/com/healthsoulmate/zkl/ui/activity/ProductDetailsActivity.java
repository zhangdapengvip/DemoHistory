package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityProductDetailsBinding;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.bean.bus.CartBus;
import com.healthsoulmate.zkl.ui.factory.FragmentFactory;
import com.healthsoulmate.zkl.ui.manager.ShoppingCartManager;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.PageShoppingGoodsResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseFragmentActivity;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.AppPagerFragmentAdapter;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.CustomViewPager;
import com.zero.library.base.view.Indicator.TabPageIndicator;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/22.
 * 商品详情
 */
public class ProductDetailsActivity extends AppBaseFragmentActivity {

    private ActivityProductDetailsBinding mBinding;
    private TabPageIndicator mTitleTab;
    private CustomViewPager mPageContent;
    private List<AppPagerFragment> mProductFragments;
    private ProductBean mDetailInfo;
    private boolean isFromCart;

    @Override
    public int getResLayout() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        Intent intent = getIntent();
        mDetailInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY);
        isFromCart = intent.getBooleanExtra(AppConstants.EXTRA_BOOLEAN, false);
        if (null == mDetailInfo) JumpManager.doJumpBack(mActivity);

        mBinding = (ActivityProductDetailsBinding) viewDataBinding;
        View tabContent = UtilsUi.inflate(mActivity, R.layout.item_product_titletab);
        mTitleTab = (TabPageIndicator) tabContent.findViewById(R.id.tab_indicator);
        TitleBarView mTitleBar = mBinding.titleBar;
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.addTitleView(tabContent);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mPageContent = mBinding.pagerContent;
        mPageContent.setEnableScroll(true);
        mProductFragments = FragmentFactory.createProductFragments(mDetailInfo);
        mPageContent.setOffscreenPageLimit(mProductFragments.size());
        mPageContent.setAdapter(new AppPagerFragmentAdapter(getSupportFragmentManager(), mProductFragments));
        mTitleTab.setViewPager(mPageContent);
        mBinding.btnAddtoCart.setOnClickListener(v -> addToCart());
        mBinding.tvCart.setOnClickListener(v -> {
            if (isFromCart) {
                JumpManager.doJumpBack(mActivity);
            } else {
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, ShoppingCartActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserManager.isLogin()) {
            notifyCount();
        } else {
            notifyLocalCount();
        }
    }

    private void notifyCount() {
        ShoppingCartManager.notifyCount(mBinding.tvCartCount, UtilsSharedPreference.getIntValue(
                Constants.SHOPPING_COUNT));
    }

    private void notifyLocalCount() {
        ShoppingCartManager.notifyLocalCount(mBinding.tvCartCount);
    }

    private void addToCart() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
        if (null == loginInfo) {
            mDetailInfo.setIsBuy(ProductBean.STATE_BUY);
            mDetailInfo.setBuynum(1);
            ShoppingCartManager.addProduct(mDetailInfo);
            notifyLocalCount();
        } else {
            ShoppingcartSaveRequest request = new ShoppingcartSaveRequest();
            request.setPkUser(loginInfo.getDatas().getPkUser());
            List<ProductBean> items = new ArrayList<>();
            mDetailInfo.setIsBuy(ProductBean.STATE_BUY);
            mDetailInfo.setBuynum(1);
            mDetailInfo.setPkUser(loginInfo.getDatas().getPkUser());
            items.add(mDetailInfo);
            request.setItems(items);
            Observable<PageShoppingGoodsResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .shoppingcartSaveRequest(request);
            RetrofitUtils.request(mActivity, ob, mBinding.btnAddtoCart,
                    new RetrofitUtils.ResponseListener<PageShoppingGoodsResponse>() {
                        @Override
                        public void beforRequest() {
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(PageShoppingGoodsResponse response) {
                            RxBus.getInstance().send(new CartBus(response.getDatas().getCountNum()));
                            notifyCount();
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

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        int currentItem = mPageContent.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            mPageContent.setCurrentItem(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProductFragments.clear();
    }
}
