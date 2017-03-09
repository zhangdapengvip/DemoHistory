package com.zero.library.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zero.library.R;
import com.zero.library.base.debug.DebugListener;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.picasso.UtilsPicasso;

public class TitleBarView extends LinearLayout {

    private LayoutInflater mInflater;
    private View mTitleLayout;
    private LinearLayout mLeft;
    private ImageView mLeftIcon;
    private TextView mLeftText;
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private LinearLayout mRight;
    private ImageView mRightIcon;
    private TextView mRightText;
    private static final float TEXT_SIZE = 10;
    private static final int DRAWABLE_SIZE = 22;
    private static final int DRAWABLE_PADDING = 3;

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mTitleLayout = mInflater.inflate(R.layout.wgt_title_bar_layout, this);
        mStatusBar = mTitleLayout.findViewById(R.id.status_bar);
        mLeft = (LinearLayout) mTitleLayout.findViewById(R.id.left);
        mLeftIcon = (ImageView) mTitleLayout.findViewById(R.id.left_icon);
        mLeftText = (TextView) mTitleLayout.findViewById(R.id.left_text);
        mTitleContainer = (LinearLayout) mTitleLayout
                .findViewById(R.id.title_container);
        mTitleContainer.setOnClickListener(titleClickListener);
        mTitle = (TextView) mTitleLayout.findViewById(R.id.title);
        mRight = (LinearLayout) mTitleLayout.findViewById(R.id.right);
        mRightIcon = (ImageView) mTitleLayout.findViewById(R.id.right_icon);
        mRightText = (TextView) mTitleLayout.findViewById(R.id.right_text);
        // 设置铺满全屏时打开
        // if (Build.VERSION.SDK_INT >= 19) {
        // mStatusBar.setVisibility(View.VISIBLE);
        // LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
        // LayoutParams.MATCH_PARENT, UtilsUi.getStatusBarHeight());
        // mStatusBar.setBackgroundResource(R.drawable.bg_statusbar);
        // mStatusBar.setLayoutParams(lParams);
        // }
    }

    public void openDebug(DebugListener listener) {
        mDebugListener = listener;
        mTitle.setOnClickListener(titleClickListener);
    }

    private long clicktime = 0;
    private int triggerTimes = 8;
    private int clickCount = 0;
    private OnClickListener titleClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            long tm = System.currentTimeMillis();
            if (tm - clicktime < 1000) {
                clickCount++;
            } else {
                clickCount = 1;
            }
            clicktime = tm;
            if (null != mDebugListener && clickCount >= triggerTimes) {
                mDebugListener.onDebugClick();
                return;
            }
        }
    };
    private DebugListener mDebugListener;
    private View mStatusBar;

    public void setLeftListener(OnClickListener listener) {
        mLeft.setOnClickListener(listener);
    }

    public void setRightListener(OnClickListener listener) {
        mRight.setOnClickListener(listener);
    }

    public LinearLayout getRightView() {
        return mRight;
    }

    public LinearLayout getLeftView() {
        return mLeft;
    }

    public void addLeftView(View view) {
        mLeft.removeAllViews();
        mLeft.addView(view);
    }

    public void addTitleView(View view) {
        mTitleContainer.removeAllViews();
        mTitleContainer.addView(view);
    }

    public void addRightView(View view) {
        setRightVisible(View.VISIBLE);
        mRight.removeAllViews();
        mRight.addView(view);
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setTitle(int resId) {
        mTitle.setText(resId);
    }

    public void setLeftVisible(int visible) {
        mLeft.setVisibility(visible);
    }

    public void setLeftIcon(Drawable drawable) {
        setLeftVisible(View.VISIBLE);
        mLeftIcon.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.GONE);
        setBackgroud(mLeftIcon, drawable);
    }

    public void setLeftIcon(int resid) {
        setLeftVisible(View.VISIBLE);
        mLeftIcon.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.GONE);
        mLeftIcon.setBackgroundResource(resid);
    }

    public void setLeftIcon(Context context, String url) {
        setLeftVisible(View.VISIBLE);
        mLeftIcon.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.GONE);
        UtilsPicasso.loadCircleImage(context, url, R.drawable.ic_default_head, mLeftIcon, R.dimen.dimen_25);
    }

    public void setLeftText(CharSequence leftText) {
        setLeftVisible(View.VISIBLE);
        mLeftIcon.setVisibility(View.GONE);
        mLeftText.setVisibility(View.VISIBLE);
        mLeftText.setText(leftText);
    }

    public void setLeftText(int resId) {
        setLeftText(UtilsUi.getString(resId));
    }

    public void setLeftText(CharSequence leftText, int icRes) {
        setLeftText(leftText);
        mLeftText.setTextSize(TEXT_SIZE);
        Drawable topDrawable = getResources().getDrawable(icRes);
        topDrawable.setBounds(0, 0, UtilsUi.dip2px(DRAWABLE_SIZE), UtilsUi.dip2px(DRAWABLE_SIZE));
        mLeftText.setCompoundDrawables(null, topDrawable, null, null);
        mLeftText.setCompoundDrawablePadding(DRAWABLE_PADDING);
    }

    public void setLeftText(int resId, int icRes) {
        setLeftText(UtilsUi.getString(resId), icRes);

    }


    public void setRightVisible(int visible) {
        mRight.setVisibility(View.VISIBLE);
    }

    public void setRightIcon(Drawable drawable) {
        setRightVisible(View.VISIBLE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.GONE);
        setBackgroud(mRightIcon, drawable);
    }

    public void setRightIcon(int resid) {
        setRightVisible(View.VISIBLE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.GONE);
        mRightIcon.setBackgroundResource(resid);
    }

    public void setRightText(CharSequence rightText) {
        setRightVisible(View.VISIBLE);
        mRightIcon.setVisibility(View.GONE);
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText(rightText);
    }

    public void setRightText(int resId) {
        setRightText(UtilsUi.getString(resId));
    }

    public void setRightText(int resId, int icRes) {
        setRightText(UtilsUi.getString(resId), icRes);
    }

    public void setRightText(CharSequence rightText, int icRes) {
        setRightText(rightText);
        mRightText.setTextSize(TEXT_SIZE);
        Drawable topDrawable = getResources().getDrawable(icRes);
        topDrawable.setBounds(0, 0, UtilsUi.dip2px(DRAWABLE_SIZE), UtilsUi.dip2px(DRAWABLE_SIZE));
        mRightText.setCompoundDrawables(null, topDrawable, null, null);
        mRightText.setCompoundDrawablePadding(DRAWABLE_PADDING);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void setBackgroud(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT > 15) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
