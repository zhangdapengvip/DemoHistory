package com.zero.library.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ZeroAries on 2016/3/11.
 * 自定义viewpage,由{@link #setEnableScroll(boolean)}设置是否左右滑动
 */
public class CustomViewPager extends ViewPager {

    private boolean isEnableScroll = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isEnableScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isEnableScroll && super.onInterceptTouchEvent(event);
    }

    public void setEnableScroll(boolean b) {
        this.isEnableScroll = b;
    }
}