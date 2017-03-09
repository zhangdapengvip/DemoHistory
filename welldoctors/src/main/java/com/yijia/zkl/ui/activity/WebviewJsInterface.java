package com.yijia.zkl.ui.activity;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.zero.library.base.view.AppWebView;


/**
 * Created by ZeroAries on 2016/3/14.
 * WebView js回调
 */
public class WebviewJsInterface {
    private Activity mActivity;
    private AppWebView mWebView;

    public WebviewJsInterface(Activity activity, AppWebView webView) {
        this.mActivity = activity;
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void jsName(String element) {
    }
}