package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityRegistBinding;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.GetMessageSendRequest;
import com.healthsoulmate.zkl.ui.protocol.request.RegisteRequest;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/3/14.
 * 注册
 */
public class RegistActivity extends AppBaseActivity {

    private static final int TIME_COUNT = 60;

    private EditText mRegistUsername;
    private EditText mRegistPassword;
    private EditText mReigstConfirm;
    private EditText mPhoneCode;
    private TextView mTvGetPhoneCode;
    private CheckBox mCheckRegist;
    private Subscription mGetCodeSub;
    private EditText mEtNickName;
    private ActivityRegistBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_regist;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityRegistBinding) viewDataBinding;
        mRegistUsername = mBinding.etRegistUsername;
        mRegistPassword = mBinding.etReigistPassword;
        mReigstConfirm = mBinding.etRegistConfirm;
        mPhoneCode = mBinding.etPhoneCode;
        mTvGetPhoneCode = mBinding.tvGetPhonecode;
        mEtNickName = mBinding.etReigistNickname;
        mCheckRegist = mBinding.cbReigst;
        mTvGetPhoneCode.setOnClickListener(v -> getPhoneCode());
        mBinding.btnRegistCommit.setOnClickListener(v -> doRegist());
        mBinding.btnBackLogin.setOnClickListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.tvState.setText(Html.fromHtml(UtilsUi.getString(R.string.label_declare)));
        mBinding.btnBackLogin.setText(Html.fromHtml(UtilsUi.getString(R.string.label_back_login)));
    }

    @Override
    public void initData() {

    }

    private void doRegist(){
        Intent intent = new Intent();
        intent.putExtra(Constants.USER_NAME, "18610275274");
        intent.putExtra(Constants.PASSWORD, "111111");
        setResult(RESULT_OK, intent);
        JumpManager.doJumpBack(mActivity);
    }

    private void doRegist1() {
        String userName = mRegistUsername.getText().toString().trim();
        String phoneCode = mPhoneCode.getText().toString().trim();
        String nickName = mEtNickName.getText().toString().trim();
        String passWord = mRegistPassword.getText().toString().trim();
        String confirm = mReigstConfirm.getText().toString().trim();
        boolean checked = mCheckRegist.isChecked();
        if (UtilsString.checkRegex(mActivity, userName, UtilsString.PHONEEGEX, R.string.error_phone)) {
            return;
        }
        if (UtilsString.checkEmpty(mActivity, mEtNickName)
                || UtilsString.checkEmpty(mActivity, mRegistPassword)
                || UtilsString.checkEmpty(mActivity, mPhoneCode)
                || UtilsString.checkEmpty(mActivity, mReigstConfirm)) {
            return;
        }
        if (!passWord.equals(confirm)) {
            AppToast.show(mActivity, R.string.toast_password_confirm);
        }
        if (UtilsString.matchString(UtilsString.CHINESEREGEX, passWord)) {
            AppToast.show(mActivity, R.string.toast_password_chiness);
            return;
        }
        if (!checked) {
            AppToast.show(mActivity, R.string.toast_agree_protocol);
            return;
        }
        RegisteRequest request = new RegisteRequest();
        request.setUserName(userName);
        request.setNumber(phoneCode);
        request.setPassword(passWord);
        request.setName(nickName);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).registRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnRegistCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse registeResponse) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.USER_NAME, request.getUserName());
                        intent.putExtra(Constants.PASSWORD, request.getPassword());
                        setResult(RESULT_OK, intent);
                        JumpManager.doJumpBack(mActivity);
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

    private void getPhoneCode() {
        String phone = mRegistUsername.getText().toString().trim();
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
