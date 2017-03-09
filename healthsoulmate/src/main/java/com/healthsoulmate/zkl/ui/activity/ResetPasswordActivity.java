package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.widget.LinearLayout;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityResetPasswordBinding;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.UpdatePasswordRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsString;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/1.
 * 重置密码
 */
public class ResetPasswordActivity extends AppBaseActivity {

    private LoginResponse mLoginInfo;
    private LinearLayout mRightView;
    private ActivityResetPasswordBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityResetPasswordBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_chage_password);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText(R.string.ok);
        mBinding.titleBar.setRightListener(v -> commitPassword());
        mRightView = mBinding.titleBar.getRightView();
    }


    private void commitPassword() {
        mLoginInfo = UserManager.getLoginInfo(mActivity);
        String oldPassword = mBinding.etOldPassword.getText().toString().trim();
        String newPassword = mBinding.etNewPassword.getText().toString().trim();
        String confirmPassword = mBinding.etNewConfirm.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, mBinding.etOldPassword)
                || UtilsString.checkEmpty(mActivity, mBinding.etNewPassword)) {
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
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        intent.putExtra(Constants.USER_NAME, UtilsSharedPreference.getStringValue(Constants.USER_NAME));
                        intent.putExtra(Constants.PASSWORD, request.getNewpassword());
                        UserManager.logOutApp(mActivity);
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
    }
}