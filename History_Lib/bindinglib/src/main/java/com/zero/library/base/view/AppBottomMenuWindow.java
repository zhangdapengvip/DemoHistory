package com.zero.library.base.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zero.library.R;

public class AppBottomMenuWindow extends AppPopupWindow {

    private LinearLayout contentView;
    private OnMenuItemCheckListener onMenuItemCheckListener;
    private Context context;

    public void setOnMenuItemCheckListener(
            OnMenuItemCheckListener onMenuItemCheckListener) {
        this.onMenuItemCheckListener = onMenuItemCheckListener;
    }

    public interface OnMenuItemCheckListener {
        void checkPosition(int position);
    }

    public AppBottomMenuWindow(Activity context) {
        super(context);
    }

    public AppBottomMenuWindow(Activity context, int resource) {
        super(context);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = (LinearLayout) inflater.inflate(resource, null);
        contentView.setOrientation(LinearLayout.VERTICAL);
    }

    public void setLayoutPadding(int left, int top, int right, int bottom) {
        contentView.setPadding(left, top, right, bottom);
    }

    public void addChild(View... child) {
        for (int i = 0; i < child.length; i++) {
            contentView.addView(child[i]);
            final int position = i;
            child[i].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onMenuItemCheckListener.checkPosition(position);
                }
            });
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Activity activity = (Activity) context;
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        setContentView(contentView);
        setWidth(displayMetrics.widthPixels - 40);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.bg_transparent));
    }
}
