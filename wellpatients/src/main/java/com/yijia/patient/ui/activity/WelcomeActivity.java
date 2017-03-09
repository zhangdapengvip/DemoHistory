package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.TextUtils;

import com.yijia.patient.R;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.GetVersionRequest;
import com.yijia.patient.ui.protocol.response.GetVersionResponse;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultRequest;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.utils.UtilsFile;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.UtilsUpdate;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;

/**
 * Created by ZeroAries on 2016/3/10.
 * 欢迎界面
 */
public class WelcomeActivity extends DefaultActivity {

    public static final String SAVE_TAG = WelcomeActivity.class.getSimpleName() + UtilsUi.getVersionName();
    private static final int TIME_SPACE = 2000;
    private Subscription mSubscription;

    @Override
    public int getResLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        long startTime = SystemClock.elapsedRealtime();
        File file = new File(UtilsUpdate.UPDATE_PATH);
        if (file.exists()) file.delete();
        Observable<GetVersionResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .userGetversionRequest(new GetVersionRequest());
        RetrofitUtils.request(mActivity, ob, new RetrofitUtils.ResponseListener<GetVersionResponse>() {
            @Override
            public void beforRequest() {
            }

            @Override
            public void onSuccess(GetVersionResponse response) {
                if (!UtilsUpdate.updateApk(mActivity, response.getDatas().getUrl(), response.getDatas().getVersion())) {
                    doNext(startTime);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onFinish(boolean isSuccess) {
                if (!isSuccess) {
                    doNext(startTime);
                }
            }
        }, false);
    }

    private void doNext(long startTime) {
        long endTime = SystemClock.elapsedRealtime();
        mSubscription = Observable.timer(TIME_SPACE + startTime - endTime, TimeUnit.MILLISECONDS).subscribe(l -> {
            doJump();
        });
    }

    private void doJump() {
        SharedPreferences sp = UtilsSharedPreference.getSharedPreference(UtilsSharedPreference.OTHER_SETTING);
        boolean notFirst = sp.getBoolean(SAVE_TAG, false);
        boolean autoLogin = UserManager.isAutoLogin();
        LoginResponse loginInfo = UserManager.getLoginInfo();
        if (!notFirst) {
            JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, GuidePageActivity.class));
        } else if (autoLogin && !TextUtils.isEmpty(loginInfo.getDatas().getPkUser())) {
            JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, MainTabActivity.class));
        } else {
            JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSubscription) mSubscription.unsubscribe();
        UtilsFile.deleteAllByDirectory(new File(DirConstants.DEFAULT_APP_DIR));
    }
}