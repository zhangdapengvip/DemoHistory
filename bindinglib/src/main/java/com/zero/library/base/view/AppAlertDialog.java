package com.zero.library.base.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zero.library.R;

public class AppAlertDialog extends AppEmptyDialog {

    private TextView mTitleView;
    private TextView mMsgView;
    private ImageView mMsgViewIV;

    public AppAlertDialog(Activity context, int btnNum) {
        super(context, btnNum);
        View view = LayoutInflater.from(context).inflate(
                R.layout.wgt_dlg_alert_layout, null);
        this.mMainLayout.addView(view, 0);

        mTitleView = (TextView) view.findViewById(R.id.dlg_alert_title);
        mMsgView = (TextView) view.findViewById(R.id.dlg_alert_msg);
        mMsgViewIV = (ImageView) view.findViewById(R.id.dlg_alert_image);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (TextUtils.isEmpty(mTitleView.getText().toString())) {
                    mTitleView.setVisibility(View.GONE);
                } else {
                    mTitleView.setVisibility(View.VISIBLE);
                }
                if (mMsgView.getLineCount() == 1) {
                    mMsgView.setGravity(Gravity.CENTER);
                } else {
                    mMsgView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                }
            }
        });
    }

    public void setTitle(String title) {
        mTitleView.setText(title == null ? "" : title);
    }

    public void setTitle(int resid) {
        mTitleView.setText(resid);
    }

    public void setMsg(String msg) {
        mMsgView.setText(msg == null ? "" : msg);
    }

    public void setMsg(int resid) {
        mMsgView.setText(resid);
    }

    public void setMsgImage(int resid) {
        mMsgViewIV.setBackgroundResource(resid);
        mMsgViewIV.setVisibility(View.VISIBLE);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }
}
