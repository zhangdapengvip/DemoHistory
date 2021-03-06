package com.zero.library.base.uibase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppWebView;

import java.util.Locale;

/**
 * Created by ZeroAries on 2016/4/5.
 * 加载网页设置
 */
public class WebViewConfiger {
    private Activity mActivity;
    private AppWebView mWebView;

    public WebViewConfiger(Activity activity, AppWebView webview) {
        mActivity = activity;
        mWebView = webview;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    public WebSettings initWebView() {
        mWebView.setWebChromeClient(new MyChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        WebSettings settings = mWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setBlockNetworkImage(true);
        // 设置支持javascript脚本
        settings.setJavaScriptEnabled(true);
        // 允许访问文件
        settings.setAllowFileAccess(true);
        // 设置显示缩放按钮
        // settings.setBuiltInZoomControls(true);
        // 支持缩放
        // settings.setSupportZoom(true);
        settings.setSaveFormData(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(DirConstants.DEFAULT_CATCH_DIR);
        // 设置缓存
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(DirConstants.DEFAULT_CATCH_DIR);
        if (!UtilsUi.isNetworkConnected(mActivity)) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 设置图片延时加载 <19
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            settings.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            settings.setDisplayZoomControls(true);
        } else {
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
            // settings.setTextZoom(70);
        } else {
            // settings.setTextSize(WebSettings.TextSize.SMALLER);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            try {
                mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            } catch (NoSuchMethodError e) {
            }
        }
        mWebView.initAppWebView(new AppWebView.IScrollInterface() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
            }
        });
        return settings;
    }

    public class MyChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Logger.v(consoleMessage.message() + " -- From line " + consoleMessage.lineNumber() + " of " +
                    consoleMessage
                            .sourceId());
            return true;
        }
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String tempUrl = url.toLowerCase(Locale.getDefault());
            if (tempUrl.startsWith("tel:")) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse(tempUrl));
                JumpManager.doJumpForward(mActivity, phoneIntent);
                return true;
            }
            if (tempUrl.startsWith("sms:")) {
                Intent itent = new Intent(Intent.ACTION_SENDTO, Uri.parse(tempUrl));
                JumpManager.doJumpForward(mActivity, itent);
                return true;
            }
            view.loadUrl(tempUrl);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.getSettings().setLoadsImagesAutomatically(true);
            view.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}
