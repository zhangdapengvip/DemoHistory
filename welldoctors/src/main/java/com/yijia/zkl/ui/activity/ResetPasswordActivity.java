package com.yijia.zkl.ui.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yijia.zkl.R;
import com.zero.library.base.uibase.DefaultActivity;
import com.yijia.zkl.ui.bean.Constants;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.UpdatePasswordRequest;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppActivityManager;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/21.
 * 修改密码
 */
public class ResetPasswordActivity extends DefaultActivity {

    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtConfirmPassword;
    private LoginResponse mLoginInfo;
    private LinearLayout mRightView;

    @Override
    public int getResLayout() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_chage_password);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        titleBar.setRightText(R.string.ok);
        titleBar.setRightListener(v1 -> commitPassword());
        mRightView = titleBar.getRightView();
        mEtOldPassword = (EditText) findViewById(R.id.et_old_password);
        mEtNewPassword = (EditText) findViewById(R.id.et_new_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.et_new_confirm);
    }

    private void commitPassword() {
        String oldPassword = mEtOldPassword.getText().toString().trim();
        String newPassword = mEtNewPassword.getText().toString().trim();
        String confirmPassword = mEtConfirmPassword.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, oldPassword, R.string.toast_password_empty)
                || UtilsString.checkEmpty(mActivity, newPassword, R.string.toast_newpassword_empty)) {
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            AppToast.show(mActivity, R.string.toast_password_confirm);
            return;
        }
        if (UtilsString.matchString(UtilsString.CHINESEREGEX, newPassword)) {
            AppToast.show(mActivity, R.string.toast_password_chiness);
            return;
        }

        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setOldpasswword(oldPassword);
        request.setNewpassword(newPassword);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).updatePasswordRequest(request);
        RetrofitUtils.request(mActivity, ob, mRightView,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppActivityManager.exitAPP();
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        intent.putExtra(Constants.USER_NAME, UtilsSharedPreference.getStringValue(Constants.USER_NAME));
                        intent.putExtra(Constants.PASSWORD, request.getNewpassword());
                        UtilsSharedPreference.clear();
                        JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, LoginActivity.class));
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

    @Override
    public void initData() {
        mLoginInfo = UserManager.getLoginInfo();
    }
}
