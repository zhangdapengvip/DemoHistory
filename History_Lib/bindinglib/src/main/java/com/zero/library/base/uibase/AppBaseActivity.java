package com.zero.library.base.uibase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.view.AppProgressDialog;

public abstract class AppBaseActivity extends Activity {
    protected Activity mActivity;
    protected Context mContext;
    protected AppProgressDialog mProgressDialog;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            // 设置顶部铺满全屏  可导致adjustResize失效
            // getWindow().setFlags(
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置底部铺满
            // getWindow().setFlags(
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, getResLayout());
        mActivity = this;
        mContext = this;
        mProgressDialog = new AppProgressDialog(mActivity);
        AppActivityManager.addActivity(mActivity);
        initView(viewDataBinding);
        initData();
    }

    /**
     * 获取布局文件资源ID
     *
     * @return 资源ID
     */
    public abstract int getResLayout();

    /**
     * 用于初始化View在{@link #initData()}之前调用
     *
     * @param viewDataBinding ViewDataBinding对象
     * @see ViewDataBinding
     */
    public abstract void initView(ViewDataBinding viewDataBinding);

    /**
     * 用于初始化Data在{@link #initView(ViewDataBinding)}之后调用
     */
    public abstract void initData();


    public void showProgressDialog() {
        if (null != mProgressDialog && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void showProgressDialog(int resId) {
        if (null != mProgressDialog && !mProgressDialog.isShowing()) {
            mProgressDialog.setTitle(getString(resId));
            mProgressDialog.show();
        }
    }

    public void hidenProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityManager.removeActivity(mActivity);
    }

    @Override
    public void onBackPressed() {
        JumpManager.doJumpBack(mActivity);
    }
}
