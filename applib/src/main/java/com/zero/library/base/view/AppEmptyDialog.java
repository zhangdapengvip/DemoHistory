package com.zero.library.base.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zero.library.R;
import com.zero.library.base.utils.UtilsUi;

public class AppEmptyDialog extends Dialog implements View.OnClickListener {
    // 主体内容布局
    protected LinearLayout mMainLayout;
    // 两个按钮布局
    private View mTwoBtnLayout;
    private Button mLeftBtn, mRightBtn, mOneBtn;
    private BtnOnClickListener mListener;
    private Activity mActivity;
    private final View mBottomLine;
    private final View mBtnContent;

    public AppEmptyDialog(Activity context, int btnNum) {
        super(context, R.style.app_dialog_style);
        setContentView(R.layout.wgt_empty_dialog_layout);
        mActivity = context;
        mMainLayout = (LinearLayout) findViewById(R.id.app_dialog_main);
        mTwoBtnLayout = findViewById(R.id.app_dialog_two_btn);
        mBtnContent = findViewById(R.id.ll_btn_content);

        mBottomLine = findViewById(R.id.app_dialog_main_btn_line);
        mLeftBtn = (Button) findViewById(R.id.app_dialog_left_btn);
        mLeftBtn.setOnClickListener(this);
        mRightBtn = (Button) findViewById(R.id.app_dialog_right_btn);
        mRightBtn.setOnClickListener(this);
        mOneBtn = (Button) findViewById(R.id.app_dialog_one_btn);
        mOneBtn.setOnClickListener(this);

        setBtnNum(btnNum);
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (UtilsUi.getScreenWidth(context) * 0.9); // 设置宽度
        this.getWindow().setAttributes(lp);
    }

    public void setBtnVisibility(int visibility) {
        mBtnContent.setVisibility(visibility);
    }

    /**
     * 设置主体内容view
     *
     * @param view
     */
    public void setView(View view) {
        this.mMainLayout.removeAllViews();
        this.mMainLayout.addView(view, 0);
    }

    public void removeView() {
        this.mMainLayout.removeAllViews();
    }

    /**
     * 显示的按钮数量（最多只有两个）
     *
     * @param num
     */
    private void setBtnNum(int num) {
        switch (num) {
            case 2:
                mOneBtn.setVisibility(View.GONE);
                mTwoBtnLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                mOneBtn.setVisibility(View.VISIBLE);
                mTwoBtnLayout.setVisibility(View.GONE);
                break;
            case 0:
                mBottomLine.setVisibility(View.GONE);
                mOneBtn.setVisibility(View.GONE);
                mTwoBtnLayout.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 设置左按钮文字
     *
     * @param resid
     */
    public void setLeftBtnText(int resid) {
        mLeftBtn.setText(resid);
    }

    /**
     * 设置左按钮文字
     *
     * @param text
     */
    public void setLeftBtnText(String text) {
        mLeftBtn.setText(text);
    }

    /**
     * 设置右按钮文字
     *
     * @param text
     */
    public void setRightBtnText(String text) {
        mRightBtn.setText(text);
    }

    /**
     * 设置右按钮文字
     *
     * @param resid
     */
    public void setRightBtnText(int resid) {
        mRightBtn.setText(resid);
    }

    /**
     * 设置只显示一个按钮情况下的按钮文字
     *
     * @param resid
     */
    public void setOneBtnText(int resid) {
        mOneBtn.setText(resid);
    }

    /**
     * 设置只显示一个按钮情况下的按钮文字
     *
     * @param text
     */
    public void setOneBtnText(String text) {
        mOneBtn.setText(text);
    }

    public void setBtnOnClickListener(BtnOnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        BtnView view = null;
        if (v.getId() == R.id.app_dialog_left_btn) {
            view = BtnView.LEFT;
        } else if (v.getId() == R.id.app_dialog_right_btn) {
            view = BtnView.RIGHT;
        } else if (v.getId() == R.id.app_dialog_one_btn) {
            view = BtnView.ONE;
        }
        if (mListener != null) {
            mListener.onClick(AppEmptyDialog.this, view);
        } else {
            dismiss();
        }
    }

    @Override
    public void show() {
        if (null == mActivity || mActivity.isFinishing() || isShowing()) {
            return;
        }
        super.show();
    }

    public enum BtnView {
        ONE, LEFT, RIGHT
    }

    public interface BtnOnClickListener {
        void onClick(Dialog dialog, BtnView view);
    }
}