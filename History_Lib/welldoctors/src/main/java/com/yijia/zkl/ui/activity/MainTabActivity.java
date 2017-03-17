package com.yijia.zkl.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RadioGroup;

import com.orhanobut.logger.Logger;
import com.yijia.zkl.R;
import com.yijia.zkl.ui.bean.Constants;
import com.yijia.zkl.ui.factory.FragmentFactory;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.GetVersionRequest;
import com.yijia.zkl.ui.protocol.request.LoginRequest;
import com.yijia.zkl.ui.protocol.request.OtherLoginRequest;
import com.yijia.zkl.ui.protocol.response.GetVersionResponse;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.manager.FragmentManager;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultRequest;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.DefaultFragmentActivity;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.UtilsUpdate;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;


/**
 * Created by ZeroAries on 2016/3/14.
 * 主页面
 */
public class MainTabActivity extends DefaultFragmentActivity {

    private RadioGroup mTabsGroup;
    private Dialog mAuthDialog;
    private Dialog mAuthingDialog;
    private LoginResponse mLoginInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_main_tab;
    }

    @Override
    public void initView() {
        mLoginInfo = UserManager.getLoginInfo();
        mTabsGroup = (RadioGroup) findViewById(R.id.tabs_group);
        new FragmentManager(this, FragmentFactory.createHomeFragments(), R.id.tab_content, mTabsGroup) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                super.onCheckedChanged(radioGroup, checkedId);
                if (checkedId == R.id.tab_tong || checkedId == R.id.tab_concur) {
                    checkAuth();
                }
            }
        };
        mAuthDialog = UtilsUi.getNoticeDialog(mActivity, "", UtilsUi.getString(R.string.notice_prove_msg),
                UtilsUi.getString(R.string.notice_prove_btn), (dialog, views) -> {
                    mTabsGroup.check(R.id.tab_news);
                    JumpManager.doJumpForward(mActivity, new Intent(mActivity, AuthenticationActivity.class));
                    dialog.dismiss();
                });
        mAuthDialog.setCancelable(false);
        mAuthingDialog = UtilsUi.getNoticeDialog(mActivity, "", UtilsUi.getString(R.string.notice_authing_msg),
                UtilsUi.getString(R.string.ok), (dialog, views) -> {
                    mTabsGroup.check(R.id.tab_news);
                    dialog.dismiss();
                });
        initJPush();
    }

    private void initJPush() {
        JPushInterface.resumePush(mContext);
        final String pushId = mLoginInfo.getDatas().getPkUser();
        boolean hasAlias = UtilsSharedPreference.getBooleanValue(pushId, false);
        if (!hasAlias && !TextUtils.isEmpty(pushId)) {
            Logger.e("set alias" + pushId);
            JPushInterface.setAlias(mActivity, pushId, (code, alias, tags) -> {
                Logger.e(code + "==" + alias);
                switch (code) {
                    case 0:
                        UtilsSharedPreference.setBooleanValue(pushId, true);
                        break;
                    default:
                        new Handler().postDelayed(() -> initJPush(), 1000 * 60);
                }
            });
        }
    }

    private void checkAuth() {
        if (LoginResponse.TYPE_UNAUTH.equals(mLoginInfo.getDatas().getReview())) {
            mAuthDialog.show();
        } else if (LoginResponse.TYPE_AUTHING.equals(mLoginInfo.getDatas().getReview())) {
            mAuthingDialog.show();
        }
    }

    @Override
    public void initData() {
        refreshLoginInfo();
    }

    private void refreshLoginInfo() {
        String userName = UtilsSharedPreference.getStringValue(Constants.USER_NAME);
        String passWord = UtilsSharedPreference.getStringValue(Constants.PASSWORD);
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
            LoginRequest request = new LoginRequest();
            request.setUsername(userName);
            request.setPassword(passWord);
            Observable<LoginResponse> obNormal = RetrofitFactory.getRetorfit(ProtocolImp.class).loginRequest(request);
            RetrofitUtils.request(mActivity, obNormal,
                    new RetrofitUtils.ResponseListener<LoginResponse>() {
                        @Override
                        public void beforRequest() {
                        }

                        @Override
                        public void onSuccess(LoginResponse loginResponse) {
                            storeLoginInfo(loginResponse);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onFinish(boolean isSuccess) {
                        }
                    });
        } else {
            String uid = UtilsSharedPreference.getStringValue(Constants.LOGIN_ID);
            String otherType = UtilsSharedPreference.getStringValue(Constants.PLATFORM);
            if (!TextUtils.isEmpty(uid)) {
                OtherLoginRequest otherRequest = new OtherLoginRequest();
                otherRequest.setOthertype(otherType);
                otherRequest.setPk_other(uid);
                Observable<LoginResponse> obOther = RetrofitFactory.getRetorfit(ProtocolImp.class).otherLoginRequest
                        (otherRequest);
                RetrofitUtils.request(mActivity, obOther,
                        new RetrofitUtils.ResponseListener<LoginResponse>() {
                            @Override
                            public void beforRequest() {
                            }

                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                storeLoginInfo(loginResponse);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onFinish(boolean isSuccess) {
                            }
                        });
            }
        }
    }

    private void storeLoginInfo(LoginResponse loginResponse) {
        UserManager.storeLoginInfo(UtilsGson.toJson(loginResponse));
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                UtilsUpdate.updateApk(mActivity, response.getDatas().getUrl(), response.getDatas().getVersion());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onFinish(boolean isSuccess) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mAuthDialog && mAuthDialog.isShowing()) {
            mAuthDialog.dismiss();
        }
        if (null != mAuthingDialog && mAuthingDialog.isShowing()) {
            mAuthingDialog.dismiss();
        }
    }
}
