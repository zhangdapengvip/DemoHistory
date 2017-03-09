package com.zero.library.base.uibase;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZeroAries on 2016/4/7.
 * ViewPager的Adapter基类
 */
public abstract class DefaultPagerAdapter<T> extends PagerAdapter {
    private List<T> mDataList;
    private int mMaxCount = -1;

    /**
     * @param dataList 数据List
     */
    public DefaultPagerAdapter(List<T> dataList) {
        mDataList = dataList;
    }

    /**
     * 用于无限循环的viewpager
     *
     * @param dataList 数据List
     * @param count    {@code Integer.MAX_VALUE}
     */
    public DefaultPagerAdapter(List<T> dataList, int count) {
        mDataList = dataList;
        mMaxCount = count;
    }

    @Override
    public int getCount() {
        return mMaxCount == -1 ? mDataList.size() : mMaxCount;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % mDataList.size();
        View pageItem = initItemView(mDataList.get(realPosition), realPosition);
        container.addView(pageItem);
        return pageItem;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    protected abstract View initItemView(T t, int position);
}
