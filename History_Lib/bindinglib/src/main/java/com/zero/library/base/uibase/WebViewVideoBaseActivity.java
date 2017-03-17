package com.zero.library.base.uibase;

import android.content.pm.ActivityInfo;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.zero.library.R;
import com.zero.library.base.view.AppWebView;

/**
 * Created by ZeroAries on 16/3/30.
 * 加载视频基类
 */
public abstract class WebViewVideoBaseActivity extends AppBaseActivity {

    protected AppWebView mWebView;
    private FrameLayout mVideoFullView;// 全屏时视频加载view
    private WebViewVideoConfiger mConfig;

    @Override
    public int getResLayout() {
        return R.layout.activity_webview_video;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mWebView = (AppWebView) findViewById(R.id.webview_content);
        mVideoFullView = (FrameLayout) findViewById(R.id.video_fullView);
        initWebView();
        initChildView();
    }

    private void initWebView() {
        mConfig = new WebViewVideoConfiger(mActivity, mVideoFullView, mWebView);
        mConfig.initWebView();
    }

    protected abstract void initChildView();

    @Override
    public void initData() {

    }

    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            mWebView.setVisibility(View.INVISIBLE);
        } else {
            mWebView.loadUrl(url, mActivity);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConfig.release();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mConfig.inCustomView()) {
                mConfig.hideCustomView();
                return true;
            } else {
                mWebView.loadUrl("about:blank");
                mActivity.finish();
            }
        }
        return false;
    }
}
