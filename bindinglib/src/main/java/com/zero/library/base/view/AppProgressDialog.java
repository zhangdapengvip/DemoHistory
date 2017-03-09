package com.zero.library.base.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zero.library.R;

public class AppProgressDialog extends Dialog {
    public Context mContext;
    public TextView mMessage;
    public TextView mTitle;
    public TextView mProgressValue;
    public ImageView mProgress;
    public Animation mOperatingAnim;
    public Button mOkButton;

    public AppProgressDialog(Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.wgt_progress_dialog_layout);
        mContext = context;
        mTitle = (TextView) findViewById(R.id.title);
        mMessage = (TextView) findViewById(R.id.message);
        mProgress = (ImageView) findViewById(R.id.progress_bar);
        mProgressValue = (TextView) findViewById(R.id.progress_value);
        mOkButton = (Button) findViewById(R.id.dialog_one_btn);
    }

    public AppProgressDialog(Context context, String title, String message) {
        this(context);
        setMessage(message);
        setTitle(title);
    }

    public AppProgressDialog(Context context, int titleid, int messageid) {
        this(context);
        setTitle(titleid);
        setMessage(messageid);
    }

    public void initProgressDialog() {

    }

    public void setMessage(String message) {
        mMessage.setVisibility(View.VISIBLE);
        mMessage.setText(message);
    }

    public void setMessage(int resid) {
        mMessage.setVisibility(View.VISIBLE);
        mMessage.setText(resid);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        } else {
            mTitle.setVisibility(View.GONE);
        }
    }

    public void setTitle(int resid) {
        if (resid > 0) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(resid);
        } else {
            mTitle.setVisibility(View.GONE);
        }
    }

    public void setButtonText(int resId) {
        mOkButton.setText(resId);
    }

    public void setButtonText(String text) {
        mOkButton.setText(text);
    }

    public void setButtonVisible(int visible) {
        mOkButton.setVisibility(visible);
    }

    public void setButtonOnClickListener(View.OnClickListener listener) {
        mOkButton.setOnClickListener(listener);
    }

    public void setProgress(int progress) {
        int value = progress;
        if (value < 0) {
            value = 0;
        }

        if (value > 100) {
            value = 100;
        }

        mProgressValue.setVisibility(View.VISIBLE);
        mProgressValue.setText(String.valueOf(value) + "%");
    }

    public void startRotateAnimation() {
        mOperatingAnim = AnimationUtils.loadAnimation(mContext,
                R.anim.progress_rotate);
        LinearInterpolator lin = new StepLinearInterpolator();
        mOperatingAnim.setInterpolator(lin);
        mProgress.startAnimation(mOperatingAnim);
    }

    @Override
    public void show() {
        try {
            if (!isShowing()) super.show();
            startRotateAnimation();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public void dismiss() {
        try {
            if (isShowing()) super.dismiss();
            if (mOperatingAnim != null) {
                mOperatingAnim.cancel();
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public void cancel() {
        try {
            super.cancel();
            if (mOperatingAnim != null) {
                mOperatingAnim.cancel();
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    public class StepLinearInterpolator extends LinearInterpolator {

        @Override
        public float getInterpolation(float input) {
            float step = (float) 1 / 12;
            int ret = (int) (input / step);
            return ret * step;
        }
    }
}
