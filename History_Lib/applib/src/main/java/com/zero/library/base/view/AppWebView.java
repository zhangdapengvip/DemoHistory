package com.zero.library.base.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.R;

public class AppWebView extends WebView {
    private IScrollInterface mScrollInterface;
    public ProgressBar mProgressBar;
    private TextView mProgressText;
    public static final int ERROR_NONET = 0;
    private static List<String> mErrorUrls = new ArrayList<String>(3);

    static {
        mErrorUrls.add(ERROR_NONET, DirConstants.ASSETS_PATH + "error/index.html");
    }

    public void loadUrl(String url, Activity owner) {
        if (!TextUtils.isEmpty(url)) {
            if (UtilsUi.isNetworkConnected(owner)) {
                loadUrl(url);
            } else {
                loadUrl(url);
            }
        } else {
            loadErrorUrl(AppWebView.ERROR_NONET);
            Logger.e("There is no website");
        }
    }

    public void loadErrorUrl(int code) {
        try {
            String url = mErrorUrls.get(code);
            if (isErrorUrl(url)) {
                loadUrl(url);
            }
        } catch (IndexOutOfBoundsException e) {
            Logger.e(e.getMessage());
        }
    }

    public boolean handleBack() {
        boolean ret = false;
        String url = getUrl();
        if (!isErrorUrl(url)) {
            if (canGoBack()) {
                goBack();
                ret = true;
            }
        } else {
            if (canGoBack()) {
                if (canGoBackOrForward(-2)) {
                    goBackOrForward(-2);
                    ret = true;
                } else {
                    goBack();
                    ret = true;
                }
            }
        }
        return ret;
    }

    private boolean isErrorUrl(String url) {
        return mErrorUrls.contains(url);
    }

    @SuppressWarnings("deprecation")
    public void initAppWebView(IScrollInterface scrollInterface) {
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2, 0, 0));
        addView(mProgressBar);
        mProgressText = new TextView(getContext());
        mProgressText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0, 4));
        mProgressText.setGravity(Gravity.CENTER_HORIZONTAL);
        mProgressText.setText(getContext().getString(
                R.string.base_webview_onloding));
        mProgressText.setTextColor(Color.GRAY);
        addView(mProgressText);
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 80) {
                    mProgressBar.setVisibility(GONE);
                    mProgressText.setVisibility(GONE);
                } else {
                    if (mProgressBar.getVisibility() == GONE) {
                        mProgressBar.setVisibility(VISIBLE);
                    }
                    if (mProgressText.getVisibility() == GONE) {
                        mProgressText.setVisibility(VISIBLE);
                    }
                    mProgressText.setTextColor(Color.argb(255 * (80 - newProgress) / 100, 128, 128, 128));
                    mProgressBar.setProgress(newProgress);
                }
            }

            public void onReachedMaxAppCacheSize(long spaceNeeded,
                                                 long totalUsedQuota,
                                                 WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(spaceNeeded * 2);
            }
        });
        mScrollInterface = scrollInterface;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mProgressBar != null) {
            LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            mProgressBar.setLayoutParams(lp);
        }
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mScrollInterface) {
            mScrollInterface.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public AppWebView(Context context, AttributeSet attrs, int defStyle,
                      boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
    }

    public AppWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AppWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppWebView(Context context) {
        super(context);
    }

    public interface IScrollInterface {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
