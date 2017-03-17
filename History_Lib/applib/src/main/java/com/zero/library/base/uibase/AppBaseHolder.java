package com.zero.library.base.uibase;

import java.util.List;

import android.app.Activity;
import android.view.View;

public abstract class AppBaseHolder<Data> {
    protected View mRootView;
    protected int mPosition = -1;
    protected Data mData;
    protected List<? extends Data> mDataList;
    protected Activity mActivity;
    protected Object mTag;

    public AppBaseHolder(Activity activity) {
        mActivity = activity;
        mRootView = View.inflate(activity, getResLayout(), null);
        mRootView.setTag(this);
        initView(mRootView);
    }

    public View getRootView() {
        return mRootView;
    }

    public void setData(List<? extends Data> dataList, Data data, int positon) {
        mData = data;
        mDataList = dataList;
        refreshView(positon);
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
    public abstract void initView(View view);

    public void recycle() {

    }
}
