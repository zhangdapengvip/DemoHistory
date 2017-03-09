package com.zero.library.base.uibase;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

public abstract class AppBaseHolder<Data> {
    protected ViewGroup mRootView;
    protected int mPosition = -1;
    protected Data mData;
    protected List<? extends Data> mDataList;
    protected Activity mActivity;
    protected Object mTag;

    public AppBaseHolder(Activity activity) {
        mActivity = activity;
        mRootView = new FrameLayout(activity);
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mRootView.getContext()),
                getResLayout(), mRootView, true);
        mRootView.setTag(this);
        initView(mRootView, viewDataBinding);
    }

    public void setData(List<? extends Data> dataList, Data data, int positon) {
        mData = data;
        mDataList = dataList;
        refreshView(positon);
    }

    public View getRootView() {
        return mRootView;
    }

    public Data getData() {
        return mData;
    }

    public List<? extends Data> getDataList() {
        return mDataList;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Object getTag() {
        return mTag;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    protected abstract int getResLayout();

    public abstract void refreshView(int position);

    public abstract void initView(View view, ViewDataBinding viewDataBinding);

    public void recycle() {

    }
}
