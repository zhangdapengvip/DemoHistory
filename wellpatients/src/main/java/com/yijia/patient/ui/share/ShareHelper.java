package com.yijia.patient.ui.share;

import android.app.Activity;
import android.view.Gravity;

import com.umeng.socialize.UMShareListener;


public class ShareHelper {
    private Activity mActivity;

    public ShareHelper(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * UMShareAPI.get(mContext);
     * 需要在onActivityResult中调用
     * mShareAPI.onActivityResult(requestCode, resultCode, data);
     *
     * @param info     分享内容
     * @param listener 结果监听
     */
    public void share(ShareInfo info, UMShareListener listener) {
        ShareCustomBoard shareBoard = new ShareCustomBoard(mActivity, info, listener);
        shareBoard.setCanceledOnTouchOutside(true);
        shareBoard.showAtLocation(mActivity.getWindow().getDecorView(),
                Gravity.BOTTOM, 0, 0);
    }

    /**
     * 分享
     *
     * @param info 分享内容
     */
    public void share(ShareInfo info) {
        share(info, null);
    }
}
