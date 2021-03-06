package com.zero.library.base.uibase;

import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;

import com.zero.library.R;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.view.AppWebView;

/**
 * Created by ZeroAries on 2016/3/25.
 * WebView基类
 */
public abstract class WebViewBaseActivity extends AppBaseActivity {

    protected AppWebView mWebView;
    protected WebSettings mSettings;

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mWebView = (AppWebView) findViewById(R.id.webview_content);
        initWebView();
        initChildView(viewDataBinding);
    }

    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            mWebView.setVisibility(View.INVISIBLE);
        } else {
            mWebView.loadUrl(url, mActivity);
        }
    }

    protected abstract void initChildView(ViewDataBinding viewDataBinding);

    @Override
    public void initData() {

    }

    protected void initWebView() {
        new WebViewConfiger(mActivity, mWebView).initWebView();
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.handleBack()) {
            JumpManager.doJumpBack(mActivity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
