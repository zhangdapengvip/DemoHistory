package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import com.yijia.patient.R;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.WebViewBaseActivity;
import com.zero.library.base.view.TitleBarView;

/**
 * Created by ZeroAries on 2016/3/14.
 * 加载网页
 */
public class WebviewActivity extends WebViewBaseActivity {

    public static final String WEBVIEW_TITLE = "webview_title";
    public static final String WEBVIEW_URL = "webview_url";
    public static final String WEBVIEW_JSCRIPT = "webview_jscript";

    private TitleBarView mTitleBar;
    private String mTitle = "";
    private String mLoadUrl = "";
    private boolean isUseJs = false;

    @Override
    public int getResLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initChildView() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(WEBVIEW_TITLE);
        mLoadUrl = intent.getStringExtra(WEBVIEW_URL);
        isUseJs = intent.getBooleanExtra(WEBVIEW_JSCRIPT, false);
        initTitle();
        if (isUseJs) initJavascript();
        loadUrl(mLoadUrl);
    }

    @JavascriptInterface
    private void initJavascript() {
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.addJavascriptInterface(new WebviewJsInterface(mActivity, mWebView), "scrName");
    }

    private void initTitle() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getString(R.string.base_webview_title);
        }
        mTitleBar.setTitle(mTitle);
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }
}
