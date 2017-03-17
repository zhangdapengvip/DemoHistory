package com.healthsoulmate.zkl.ui.fragment.product;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.RadioGroup;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentProductDetailBinding;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.WebViewBaseActivity;
import com.zero.library.base.uibase.WebViewConfiger;

/**
 * Created by ZeroAries on 2016/4/22.
 * 商品详情
 */
public class ProductDetailFragment extends AppPagerFragment {

    private FragmentProductDetailBinding mBinding;
    private ProductBean mProductInfo;

    @Override
    public String getPagerTitle() {
        return "详情";
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_product_detail;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mProductInfo = mArguments.getParcelable(AppConstants.PARCELABLE_KEY);
        mBinding = (FragmentProductDetailBinding) inflate;
        WebSettings webSettings = new WebViewConfiger(mActivity, mBinding.webviewContent).initWebView();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        mBinding.ivUptoTop.setOnClickListener(v -> mBinding.webviewContent.scrollTo(0, 0));
        mBinding.webviewContent.loadUrl(mProductInfo.getDetailurl());
    }

    @Override
    protected void initData() {

    }
}
