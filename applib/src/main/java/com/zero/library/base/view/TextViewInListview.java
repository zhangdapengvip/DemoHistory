package com.zero.library.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.TextView;

public class TextViewInListview extends TextView {

    public TextViewInListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextViewInListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewInListview(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) FloatMath.ceil(getMaxLineHeight(this.getText().toString()))
                    + getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }
    }

    /**
     * 计算textview内容行数,返回对应行数的高度。TextView需设置属性MaxWidth为TextView宽度;
     * 
     * @param str textview 内容
     * @return
     */
    @SuppressLint("NewApi")
    private float getMaxLineHeight(String str) {
        float height = 0.0f;
        float screenW = getMaxWidth();
        int line = (int) Math.ceil((this.getPaint().measureText(str) / (screenW)));
        height = (this.getPaint().getFontMetrics().descent - this.getPaint().getFontMetrics().ascent)
                * line;
        return height;
    }

}