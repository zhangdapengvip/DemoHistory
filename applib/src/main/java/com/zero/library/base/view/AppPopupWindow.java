package com.zero.library.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;

import com.zero.library.base.utils.UtilsUi;

public class AppPopupWindow extends PopupWindow {

    private Activity mActivity;

    public AppPopupWindow(Activity context) {
        super(context);
        mActivity = context;
    }

    public AppPopupWindow(Activity activity, View view) {
        super(view);
        mActivity = activity;
    }

    public void setCanceledOnTouchOutside(boolean canCancele) {
        if (canCancele) {
            setFocusable(true);
            setBackgroundDrawable(new BitmapDrawable());
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        UtilsUi.setWindowBackgroundAlpha(mActivity, 0.6f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        UtilsUi.setWindowBackgroundAlpha(mActivity, 0.6f);
    }

    @SuppressLint("NewApi")
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        UtilsUi.setWindowBackgroundAlpha(mActivity, 0.6f);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        UtilsUi.setWindowBackgroundAlpha(mActivity, 0.6f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UtilsUi.setWindowBackgroundAlpha(mActivity, 1f);
    }
}
