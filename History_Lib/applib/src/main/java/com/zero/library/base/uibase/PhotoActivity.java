package com.zero.library.base.uibase;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;

import com.zero.library.R;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.utils.UtilsBitmap;
import com.zero.library.base.utils.UtilsFile;
import com.zero.library.base.view.AppBottomMenuWindow;
import com.zero.library.base.view.PhotoMenuWindow;

import java.io.File;

/**
 * Created by ZeroAries on 2016/3/21.
 * 拍照父类
 */
public abstract class PhotoActivity extends DefaultActivity implements AppBottomMenuWindow.OnMenuItemCheckListener {
    public static final int CAMERA_LIBRARY = 8001;
    public static final int TAKE_PHOTO = CAMERA_LIBRARY + 1;
    public static final int PHOTO_REQUEST_CUT = CAMERA_LIBRARY + 2;
    private PhotoMenuWindow mBottomMenuWindow;
    protected Uri mImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPhoneWindow();
    }

    /**
     * 初始化底部弹出按钮
     */
    private void initPhoneWindow() {
        mBottomMenuWindow = new PhotoMenuWindow(mActivity);
        mBottomMenuWindow.setOnMenuItemCheckListener(this);
        mBottomMenuWindow.setCanceledOnTouchOutside(true);
        mBottomMenuWindow.setAnimationStyle(R.style.push_bottom_anim);
    }

    /**
     * 底部按钮位置选择
     * #BUTTON_ONE 拍照
     * #BUTTON_TWO 相册
     * #BUTTON_CANCLE 取消
     *
     * @param position
     */
    @Override
    public void checkPosition(int position) {
        switch (position) {
            case PhotoMenuWindow.BUTTON_ONE:
                mImgUri = Uri.fromFile(new File(DirConstants.DEFAULT_IMG_DIR,
                        +SystemClock.elapsedRealtime() + ".jpg"));
                JumpManager.doJumpForwardWithResult(mActivity, UtilsBitmap.getCameraIntent(mImgUri), TAKE_PHOTO);
                closePhotoWindow();
                break;
            case PhotoMenuWindow.BUTTON_TWO:
                JumpManager.doJumpForwardWithResult(mActivity, UtilsBitmap.getGalleryIntent(),
                        CAMERA_LIBRARY);
                closePhotoWindow();
                break;
            case PhotoMenuWindow.BUTTON_CANCLE:
                closePhotoWindow();
                break;
        }
    }

    /**
     * 显示底部按钮
     */
    protected void showPhotoWindow() {
        if (null != mBottomMenuWindow) mBottomMenuWindow.showAtLocation(
                mActivity.getWindow().getDecorView(),
                Gravity.BOTTOM, 0, 0);
    }

    /**
     * 关闭底部按钮
     *
     * @return
     */
    protected boolean closePhotoWindow() {
        if (mBottomMenuWindow != null && mBottomMenuWindow.isShowing()) {
            mBottomMenuWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closePhotoWindow();
        UtilsFile.deleteAllByDirectory(new File(DirConstants.DEFAULT_IMG_DIR));
    }
}
