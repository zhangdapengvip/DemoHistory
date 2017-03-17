package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityLoginBinding;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.bean.bus.LoginBus;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.LoginRequest;
import com.healthsoulmate.zkl.ui.protocol.request.OtherLoginRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


/**
 * Created by ZeroAries on 2016/3/14.
 * 登录
 */
public class LoginActivity extends AppBaseActivity implements View.OnClickListener {

    private final int REQUEST_REGIST = 5001;
    private final int REQUEST_PERFECT = 5002;

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
    public void initView(ViewDataBinding viewDataBinding) {
        ActivityLoginBinding mBinding = (ActivityLoginBinding) viewDataBinding;
        mShareAPI = UMShareAPI.get(mContext);
        mLoginUserName = mBinding.etLoginUsername;
        mLoginPassword = mBinding.etLoginPassword;
        mAutoLogin = mBinding.cbAutoLogin;
        mBtnWechat = mBinding.btnAuthWechat;
        mBtnWechat.setOnClickListener(this);
        mBtnSina = mBinding.btnAuthSina;
        mBtnSina.setOnClickListener(this);
        mBtnQq = mBinding.btnAuthQq;
        mBtnQq.setOnClickListener(this);
        mBtnLogin = mBinding.btnLogin;
        mBtnLogin.setOnClickListener(this);
        TextView mBtnRegist = mBinding.btnRegist;
        mBtnRegist.setText(Html.fromHtml(UtilsUi.getString(R.string.btn_register)));
        mBtnRegist.setOnClickListener(this);
        if (mShareAPI.isInstall(mActivity, SHARE_MEDIA.WEIXIN)) {
            mBinding.btnAuthWechat.setVisibility(View.VISIBLE);
        }
        if (mShareAPI.isInstall(mActivity, SHARE_MEDIA.QQ)) {
            mBinding.btnAuthQq.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
    }

    private void requestLogin() {
        String userName = mLoginUserName.getText().toString().trim();
        String passWord = mLoginPassword.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, mLoginUserName)
                || UtilsString.checkEmpty(mActivity, mLoginPassword)) {
            return;
        }
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
                        fillInfo(loginResponse, "");
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
        String uuidType = "";
        String nickName = data.get("nickName");
        View view = null;
        switch (platform) {
            case QQ:
                uid = data.get("uid");
                uuidType = "1";
                view = mBtnQq;
                break;
            case WEIXIN:
                uid = data.get("uid");
//                uid = data.get("unionid");
                uuidType = "2";
                view = mBtnWechat;
                break;
            case SINA:
                uuidType = "3";
                uid = data.get("uid");
                view = mBtnSina;
                break;
        }
        if (TextUtils.isEmpty(uid)) return;
        UtilsSharedPreference.clear();
        otherRequest.setUuidtype(uuidType);
        otherRequest.setUuid(uid);
        otherRequest.setName(nickName);
        UtilsSharedPreference.setStringValue(Constants.LOGIN_ID, uid);
        UtilsSharedPreference.setStringValue(Constants.PLATFORM, uuidType);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).otherLoginRequest(otherRequest);
        RetrofitUtils.request(mActivity, ob, view,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        fillInfo(loginResponse, nickName);
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

    private void fillInfo(LoginResponse loginResponse, String nickName) {
        boolean autoLogin = mAutoLogin.isChecked();
        UserManager.autoLogin(autoLogin);
        UserManager.storeLoginInfo(UtilsGson.toJson(loginResponse));
        if (TextUtils.isEmpty(loginResponse.getDatas().getPhone())) {
            Intent intent = new Intent(mActivity, PerfectRegistActivity.class);
            intent.putExtra(AppConstants.EXTRA_STRING, nickName);
            JumpManager.doJumpForwardWithResult(mActivity, intent, REQUEST_PERFECT);
        } else {
            RxBus.getInstance().send(new LoginBus());
            JumpManager.doJumpBack(mActivity);
        }
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
        if (null != platform) {
            AppToast.show(mActivity, "构建中,敬请期待!");
//            Map<String, String> data = new HashMap<>();
//            data.put("uid", "100861125");
//            data.put("uid", "rsdgfx555555");
//            data.put("nickName", "三方昵称");
//            requestOtherLogin(platform, data);
        }
//        if (null != platform) mShareAPI.doOauthVerify(mActivity, platform, umAuthListener);
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
        } else if (resultCode == RESULT_OK && REQUEST_PERFECT == requestCode) {
            JumpManager.doJumpBack(mActivity);
        } else if (REQUEST_PERFECT == requestCode) {
            UserManager.storeLoginInfo("");
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
