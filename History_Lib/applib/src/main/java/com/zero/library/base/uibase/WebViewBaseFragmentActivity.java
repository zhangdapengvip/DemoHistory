package com.zero.library.base.uibase;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;

import com.zero.library.R;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.view.AppWebView;

/**
 * Created by ZeroAries on 2016/3/25.
 * WebView基类
 */
public abstract class WebViewBaseFragmentActivity extends DefaultFragmentActivity {

    protected AppWebView mWebView;

    @Override
    public void initView() {
        mWebView = (AppWebView) findViewById(R.id.webview_content);
        initWebView();
        initChildView();
    }

    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            mWebView.setVisibility(View.INVISIBLE);
        } else {
            mWebView.loadUrl(url, mActivity);
        }
    }

    protected abstract void initChildView();

    @Override
    public void initData() {

    }

    private void initWebView() {
        new WebViewConfiger(mActivity).initWebView(mWebView);
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
