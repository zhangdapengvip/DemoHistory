package com.zero.library.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.library.R;
import com.zero.library.base.utils.UtilsUi;

public class AppToast {

    private View mView;
    private ImageView mIconView;
    private TextView mMsgView;
    private int mIconRes;
    private CharSequence mMsg;
    private Typeface mTypeface;
    private Toast toast;
    private static AppToast mToast;

    @SuppressLint("InflateParams")
    private AppToast(Context context) {
        mView = LayoutInflater.from(context).inflate(
                R.layout.wgt_item_app_toast_layout, null);
        mIconView = (ImageView) mView.findViewById(R.id.iv_app_toast);
        mMsgView = (TextView) mView.findViewById(R.id.tv_app_toast);
        mTypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/OpenSans-Regular.ttf");
    }

    public static AppToast getInstance(Context context) {
        if (null == mToast) {
            synchronized (AppToast.class) {
                if (null == mToast) {
                    mToast = new AppToast(context);
                }
            }
        }
        return mToast;
    }

    private void show(final Context context) {
        UtilsUi.runInMainThread(new Runnable() {

            @Override
            public void run() {
                if (null == toast)
                    toast = new Toast(context);
                toast.setView(mView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                if (mIconRes <= 0) {
                    mIconView.setVisibility(View.GONE);
                } else {
                    mIconView.setVisibility(View.VISIBLE);
                    mIconView.setImageResource(mIconRes);
                }
                if (!TextUtils.isEmpty(mMsg)) {
                    mMsgView.setText(mMsg);
                    mMsgView.setTypeface(mTypeface);
                    toast.show();
                }
            }
        });
    }

    private void showLong(final Context context) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (null == toast)
                    toast = new Toast(context);
                toast.setView(mView);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                if (mIconRes <= 0) {
                    mIconView.setVisibility(View.GONE);
                } else {
                    mIconView.setVisibility(View.VISIBLE);
                    mIconView.setImageResource(mIconRes);
                }
                if (!TextUtils.isEmpty(mMsg)) {
                    mMsgView.setText(mMsg);
                    mMsgView.setTypeface(mTypeface);
                    toast.show();
                }
            }
        });
    }

    public static void show(final Activity context, final int iconRes,
                            final CharSequence message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = iconRes;
                toast.mMsg = message;
                if (!context.isFinishing())
                    toast.show(context);
            }
        });
    }

    public static void showLong(final Activity context, final int iconRes,
                                final CharSequence message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = iconRes;
                toast.mMsg = message;
                if (!context.isFinishing())
                    toast.showLong(context);
            }
        });
    }

    public static void show(final Activity context, final CharSequence message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = -1;
                toast.mMsg = message;
                if (!context.isFinishing())
                    toast.show(context);
            }
        });
    }

    public static void showLong(final Activity context,
                                final CharSequence message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = -1;
                toast.mMsg = message;
                if (!context.isFinishing())
                    toast.showLong(context);
            }
        });
    }

    public static void show(final Activity context, final int iconRes,
                            final int message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = iconRes;
                toast.mMsg = context.getString(message);
                if (!context.isFinishing())
                    toast.show(context);
            }
        });
    }

    public static void showLong(final Activity context, final int iconRes,
                                final int message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = iconRes;
                toast.mMsg = context.getString(message);
                if (!context.isFinishing())
                    toast.showLong(context);
            }
        });
    }

    public static void show(final Activity context, final int message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = -1;
                toast.mMsg = context.getString(message);
                if (!context.isFinishing())
                    toast.show(context);
            }
        });
    }

    public static void showLong(final Activity context, final int message) {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppToast toast = getInstance(context);
                toast.mIconRes = -1;
                toast.mMsg = context.getString(message);
                if (!context.isFinishing())
                    toast.showLong(context);
            }
        });
    }

    public static void show(Context context, int iconRes, CharSequence message) {
        show((Activity) context, iconRes, message);
    }

    public static void showLong(Context context, int iconRes,
                                CharSequence message) {
        showLong((Activity) context, iconRes, message);
    }

    public static void show(Context context, CharSequence message) {
        show((Activity) context, message);
    }

    public static void showLong(Context context, CharSequence message) {
        showLong((Activity) context, message);
    }

    public static void show(Context context, int iconRes, int message) {
        show((Activity) context, iconRes, message);
    }

    public static void showLong(Context context, int iconRes, int message) {
        showLong((Activity) context, iconRes, message);
    }

    public static void show(Context context, int message) {
        show((Activity) context, message);
    }

    public static void showLong(Context context, int message) {
        showLong((Activity) context, message);
    }
}