package com.zero.library.base.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.zero.library.R;


public class JumpManager {
    public static final int NAVI_ANIMATION_FADE = 0;
    public static final int NAVI_ANIMATION_FLIP_FROM_LEFT = NAVI_ANIMATION_FADE + 1;
    public static final int NAVI_ANIMATION_FLIP_FROM_RIGHT = NAVI_ANIMATION_FADE + 2;

    public static void doJumpForwardWithResult(Activity activity,
                                               Intent intent, int requestCode, int animation) {
        activity.startActivityForResult(intent, requestCode);
        jumpAnimation(activity, animation);
    }

    public static void doJumpForwardWithResult(Activity activity,
                                               Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        jumpAnimation(activity, NAVI_ANIMATION_FLIP_FROM_RIGHT);
    }

    public static void doJumpForward(Activity activity, Intent intent,
                                     int animation) {
        activity.startActivity(intent);
        jumpAnimation(activity, animation);
    }

    public static void doJumpForward(Activity activity, Intent intent) {
        activity.startActivity(intent);
        jumpAnimation(activity, NAVI_ANIMATION_FLIP_FROM_RIGHT);
    }

    public static void doJumpForwardFinish(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.finish();
        jumpAnimation(activity, NAVI_ANIMATION_FLIP_FROM_RIGHT);
    }

    public static void doJumpForwardDelayFinish(final Activity activity,
                                                Intent intent, int delayMillis) {
        activity.startActivity(intent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                activity.finish();
            }
        }, delayMillis);
        jumpAnimation(activity, NAVI_ANIMATION_FLIP_FROM_RIGHT);
    }

    public static void doJumpBack(Activity activity) {
        activity.finish();
        jumpAnimation(activity, NAVI_ANIMATION_FLIP_FROM_LEFT);
    }

    public static void doJumpBack(Activity activity, int animation) {
        activity.finish();
        jumpAnimation(activity, animation);
    }

    private static void jumpAnimation(Context context, int animation) {
        switch (animation) {
            case NAVI_ANIMATION_FADE:
                ((Activity) context).overridePendingTransition(
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case NAVI_ANIMATION_FLIP_FROM_LEFT:
                ((Activity) context).overridePendingTransition(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                break;
            case NAVI_ANIMATION_FLIP_FROM_RIGHT:
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}
