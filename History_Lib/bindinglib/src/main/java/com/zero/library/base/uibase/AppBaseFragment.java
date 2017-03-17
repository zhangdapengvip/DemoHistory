package com.zero.library.base.uibase;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.library.base.view.AppProgressDialog;

public abstract class AppBaseFragment extends Fragment {
    protected View mContentView;
    protected Activity mActivity;
    protected AppProgressDialog mProgressDialog;
    protected boolean isCreate = false;
    protected boolean showing = false;
    protected Bundle mArguments;

    public AppBaseFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mProgressDialog = new AppProgressDialog(mActivity);
    }

    public void showProgressDialog() {
        if (null != mProgressDialog && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void showProgressDialog(boolean canCancle) {
        if (null != mProgressDialog && !mProgressDialog.isShowing()) {
            mProgressDialog.setCancelable(canCancle);
            mProgressDialog.setCanceledOnTouchOutside(canCancle);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mArguments = getArguments();
        mContentView = getContentView(inflater, container);
        isCreate = true;
        return mContentView;
    }

    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        ViewDataBinding inflate = DataBindingUtil.inflate(inflater, getResLayout(), container, false);
        View view = inflate.getRoot();
        initView(view, inflate);
        initData();
        return view;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    /**
     * 获取布局文件资源ID
     *
     * @return 资源ID
     */
    protected abstract int getResLayout();

    /**
     * 用于初始化View在{@link #initData()}之前调用
     *
     * @param view    当前页面加载的view
     * @param inflate
     */
    protected abstract void initView(View view, ViewDataBinding inflate);

    /**
     * 用于初始化Data在{@link #initView(View, ViewDataBinding)}之后调用
     */
    protected abstract void initData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
