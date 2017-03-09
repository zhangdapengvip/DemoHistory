package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.LoginRequest;
import com.yijia.patient.ui.protocol.request.OtherLoginRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;

import java.util.Map;
import java.util.Set;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 登录
 */
public class LoginActivity extends DefaultActivity implements View.OnClickListener {

    private final int REQUEST_REGIST = 5001;

    private UMShareAPI mShareAPI = null;
    private EditText mLoginUserName;
    private EditText mLoginPassword;
    private CheckBox mAutoLogin;
    private View mBtnLogin;
    private View mBtnWechat;
    private View mBtnSina;
    private View mBtnQq;

    @Override
    public int getResLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mShareAPI = UMShareAPI.get(mContext);
        mLoginUserName = (EditText) findViewById(R.id.et_login_username);
        mLoginPassword = (EditText) findViewById(R.id.et_login_password);
        mAutoLogin = (CheckBox) findViewById(R.id.cb_auto_login);
        mBtnWechat = findViewById(R.id.btn_auth_wechat);
        mBtnWechat.setOnClickListener(this);
        mBtnSina = findViewById(R.id.btn_auth_sina);
        mBtnSina.setOnClickListener(this);
        mBtnQq = findViewById(R.id.btn_auth_qq);
        mBtnQq.setOnClickListener(this);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        TextView mBtnRegist = (TextView) findViewById(R.id.btn_regist);
        mBtnRegist.setText(Html.fromHtml(UtilsUi.getString(R.string.btn_register)));
        mBtnRegist.setOnClickListener(this);
        if (mShareAPI.isInstall(mActivity, SHARE_MEDIA.WEIXIN)) {
            mBtnWechat.setVisibility(View.VISIBLE);
        }
        if (mShareAPI.isInstall(mActivity, SHARE_MEDIA.QQ)) {
            mBtnQq.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {

    }

    private void requestLogin() {
        String userName = mLoginUserName.getText().toString().trim();
        String passWord = mLoginPassword.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, userName, R.string.toast_username_empty)
                || UtilsString.checkEmpty(mActivity, passWord, R.string.toast_password_empty)) {
            return;
        }
        UtilsSharedPreference.clear();
        LoginRequest request = new LoginRequest();
        request.setUsername(userName);
        request.setPassword(passWord);
        UtilsSharedPreference.setStringValue(AppConstants.SP_AUTH_INFO, request.getUsername() + ":" +
                request.getPassword());
        UtilsSharedPreference.setStringValue(Constants.USER_NAME, request.getUsername());
        UtilsSharedPreference.setStringValue(Constants.PASSWORD, request.getPassword());
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).loginRequest(request);
        RetrofitUtils.request(mActivity, ob, mBtnLogin,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        fillInfo(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    private void requestOtherLogin(SHARE_MEDIA platform, Map<String, String> data) {
        OtherLoginRequest otherRequest = new OtherLoginRequest();
        String uid = "";
        String otherType = "";
        View view = null;
        switch (platform) {
            case QQ:
                uid = data.get("uid");
                otherType = "1";
                view = mBtnQq;
                break;
            case WEIXIN:
                uid = data.get("unionid");
                otherType = "2";
                view = mBtnWechat;
                break;
            case SINA:
                otherType = "3";
                uid = data.get("uid");
                view = mBtnSina;
                break;
        }
        UtilsSharedPreference.clear();
        otherRequest.setOthertype(otherType);
        otherRequest.setPk_other(uid);
        UtilsSharedPreference.setStringValue(AppConstants.SP_AUTH_INFO, otherRequest.getPk_other() + ":");
        UtilsSharedPreference.setStringValue(Constants.LOGIN_ID, uid);
        UtilsSharedPreference.setStringValue(Constants.PLATFORM, otherType);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).otherLoginRequest(otherRequest);
        RetrofitUtils.request(mActivity, ob, view,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        fillInfo(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    private void fillInfo(LoginResponse loginResponse) {
        boolean autoLogin = mAutoLogin.isChecked();
        UserManager.storeLoginInfo(UtilsGson.toJson(loginResponse));
        UserManager.autoLogin(autoLogin);
        JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, MainTabActivity.class));
    }

    @Override
    public void onClick(View v) {
        SHARE_MEDIA platform = null;
        switch (v.getId()) {
            case R.id.btn_login:
                requestLogin();
                break;
            case R.id.btn_regist:
                JumpManager.doJumpForwardWithResult(mActivity, new Intent(mActivity, RegistActivity.class),
                        REQUEST_REGIST);
                break;
            case R.id.btn_auth_wechat:
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.btn_auth_sina:
                platform = SHARE_MEDIA.SINA;
                break;
            case R.id.btn_auth_qq:
                platform = SHARE_MEDIA.QQ;
                break;
        }
        if (null != platform) mShareAPI.doOauthVerify(mActivity, platform, umAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && REQUEST_REGIST == requestCode) {
            String userName = data.getStringExtra(Constants.USER_NAME);
            String password = data.getStringExtra(Constants.PASSWORD);
            mLoginUserName.setText(userName);
            mLoginPassword.setText(password);
        }
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            requestOtherLogin(platform, data);
            Logger.e("Authorize succeed" + data.toString());
//            mShareAPI.getPlatformInfo(mActivity, platform, new UMAuthListener() {
//                @Override
//                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                    if (map != null && map.size() > 0) {
//                        StringBuilder sb = new StringBuilder();
//                        Set<String> keys = map.keySet();
//                        for (String key : keys) {
//                            sb.append(key + "=" + map.get(key).toString() + "\r\n");
//                        }
//                        Logger.e("info=" + sb.toString());
//                    }
//                }
//
//                @Override
//                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//                }
//
//                @Override
//                public void onCancel(SHARE_MEDIA share_media, int i) {
//
//                }
//            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Logger.e("Authorize fail" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Logger.e("Authorize cancel");
        }
    };
}
