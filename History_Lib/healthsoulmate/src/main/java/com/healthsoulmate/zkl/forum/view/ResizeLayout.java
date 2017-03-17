package com.healthsoulmate.zkl.forum.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ZeroAries on 2016/5/9.
 * 重新计算View大小
 */
public class ResizeLayout extends LinearLayout {
    public ResizeLayout(Context context) {
        super(context);
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (null != changed) changed.changed(w, h, oldw, oldh);
    }

    private OnSizeChanged changed;

    public OnSizeChanged getChanged() {
        return changed;
    }

    public void setChanged(OnSizeChanged changed) {
        this.changed = changed;
    }

    public interface OnSizeChanged {
        void changed(int w, int h, int oldw, int oldh);
    }
}
