package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.widget.TextView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityForgetPasswordBinding;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.ForgetPasswordRequest;
import com.healthsoulmate.zkl.ui.protocol.request.GetMessageSendRequest;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsString;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/5/19.
 * 忘记密码
 */
public class ForgetPasswordActivity extends AppBaseActivity {

    private static final int TIME_COUNT = 60;

    private ActivityForgetPasswordBinding mBinding;
    private Subscription mGetCodeSub;
    private TextView mTvGetPhoneCode;

    @Override
    public int getResLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityForgetPasswordBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_reset_password);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));

        mTvGetPhoneCode = mBinding.tvGetPhonecode;
        mTvGetPhoneCode.setOnClickListener(v -> getPhoneCode());
        mBinding.btnResetCommit.setOnClickListener(v -> doReset());
    }

    private void doReset() {
        String userName = mBinding.etRegistUsername.getText().toString().trim();
        String phoneCode = mBinding.etPhoneCode.getText().toString().trim();
        String passWord = mBinding.etReigistPassword.getText().toString().trim();
        String confirm = mBinding.etRegistConfirm.getText().toString().trim();
        if (UtilsString.checkRegex(mActivity, userName, UtilsString.PHONEEGEX, R.string.error_phone)) {
            return;
        }
        if (UtilsString.checkEmpty(mActivity, mBinding.etReigistPassword)
                || UtilsString.checkEmpty(mActivity, mBinding.etPhoneCode)
                || UtilsString.checkEmpty(mActivity, mBinding.etRegistConfirm)) {
            return;
        }
        if (!passWord.equals(confirm)) {
            AppToast.show(mActivity, R.string.toast_password_confirm);
        }
        if (UtilsString.matchString(UtilsString.CHINESEREGEX, passWord)) {
            AppToast.show(mActivity, R.string.toast_password_chiness);
            return;
        }

        ForgetPasswordRequest request = new ForgetPasswordRequest();
        request.setPhone(userName);
        request.setNumber(phoneCode);
        request.setPassword(passWord);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).forgetPasswordRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnResetCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.USER_NAME, request.getPhone());
                        intent.putExtra(Constants.PASSWORD, request.getPassword());
                        setResult(RESULT_OK, intent);
                        JumpManager.doJumpBack(mActivity);
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
    public void initData() {

    }

    private void getPhoneCode() {
        String phone = mBinding.etRegistUsername.getText().toString().trim();
        if (UtilsString.checkRegex(mActivity, phone, UtilsString.PHONEEGEX, R.string.error_phone)) {
            return;
        }
        GetMessageSendRequest request = new GetMessageSendRequest();
        request.setPhone(phone);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).getMessageSendRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, response.getMessage());
                        codeTime();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    private void codeTime() {
        mTvGetPhoneCode.setClickable(false);
        mGetCodeSub = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(TIME_COUNT)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    mTvGetPhoneCode.setText(getString(R.string.btn_authentication_new_phone_code, TIME_COUNT - l));
                    mTvGetPhoneCode.setBackgroundResource(R.drawable.bg_btn_uncheck);
                    if (l == TIME_COUNT - 1) {
                        mTvGetPhoneCode.setClickable(true);
                        mTvGetPhoneCode.setText(R.string.btn_authentication_get_phone_code);
                        mTvGetPhoneCode.setBackgroundResource(R.drawable.bg_btn_normal);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mGetCodeSub) mGetCodeSub.unsubscribe();
    }
}
