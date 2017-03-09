package com.healthsoulmate.zkl.ui.activity;

import android.databinding.ViewDataBinding;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityNewsDetailBinding;
import com.healthsoulmate.zkl.ui.protocol.response.NewsBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.WebViewBaseActivity;

/**
 * Created by ZeroAries on 2016/5/23.
 * 网页详情
 */
public class NewsDetailActivity extends WebViewBaseActivity {

    @Override
    public int getResLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initChildView(ViewDataBinding viewDataBinding) {
        ActivityNewsDetailBinding mBinding = (ActivityNewsDetailBinding) viewDataBinding;
        NewsBean newInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null == newInfo) JumpManager.doJumpBack(mActivity);
        mBinding.titleBar.setTitle(newInfo.getTitle());
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        loadUrl(newInfo.getInfourl());
    }
}