package com.zero.library.base.uibase;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.zero.library.R;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppWebView;

/**
 * Created by ZeroAries on 2016/4/5.
 * 加载视频设置
 */
public class WebViewVideoConfiger {
    private View mCustomView;
    private Activity mActivity;
    private AppWebView mWebView;
    private FrameLayout mVideoFullView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private MyWebChromeClient mWebChromeClient;

    public WebViewVideoConfiger(Activity activity, FrameLayout fullView, AppWebView webview) {
        mActivity = activity;
        mVideoFullView = fullView;
        mWebView = webview;
    }

    public void initWebView() {
        WebSettings settings = mWebView.getSettings();
        // 隐藏缩放按钮
        settings.setBuiltInZoomControls(false);
        // 排版适应屏幕
        //settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // 可任意比例缩放
        //settings.setUseWideViewPort(true);
        // setUseWideViewPort方法设置webview推荐使用的窗口。
        // setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        settings.setLoadWithOverviewMode(true);

        settings.setSavePassword(true);
        // 保存表单数据
        settings.setSaveFormData(true);
        settings.setJavaScriptEnabled(true);
        // 启用地理定位
        //settings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        //settings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);// 新加
        mWebChromeClient = new MyWebChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        private View progressVideo;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            UtilsUi.setFullScreen(mActivity);
            mWebView.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mVideoFullView.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mVideoFullView.setVisibility(View.VISIBLE);
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            UtilsUi.quitFullScreen(mActivity);
            if (mCustomView == null)// 不是全屏播放状态
            {
                return;
            }
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mCustomView.setVisibility(View.GONE);
            mVideoFullView.removeView(mCustomView);
            mCustomView = null;
            mVideoFullView.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();
            mWebView.setVisibility(View.VISIBLE);
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            if (progressVideo == null) {
                LayoutInflater inflater = LayoutInflater.from(mActivity);
                progressVideo = inflater.inflate(R.layout.wgt_pager_loading_onload, null);
            }
            return progressVideo;
        }
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return mCustomView != null;
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void release() {
        if (mVideoFullView.getChildCount() > 0) mVideoFullView.removeAllViews();
        ViewGroup parent = (ViewGroup) mWebView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        mWebView.loadUrl("about:blank");
        mWebView.stopLoading();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
