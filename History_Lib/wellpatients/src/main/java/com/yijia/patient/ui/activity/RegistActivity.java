package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.yijia.patient.R;
import com.zero.library.base.uibase.DefaultActivity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.RegisteRequest;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppSpinner;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 注册
 */
public class RegistActivity extends DefaultActivity implements View.OnClickListener {

    private EditText mRegistUsername;
    private EditText mRegistPassword;
    private EditText mReigstConfirm;
    private CheckBox mCheckRegist;
    private View mBtnRegist;
    private TextView mTvState;
    private TextView mTvBacklogin;

    @Override
    public int getResLayout() {
        return R.layout.activity_regist;
    }

    @Override
    public void initView() {
        mRegistUsername = (EditText) findViewById(R.id.et_regist_username);
        mRegistPassword = (EditText) findViewById(R.id.et_reigist_password);
        mReigstConfirm = (EditText) findViewById(R.id.et_regist_confirm);
        mCheckRegist = (CheckBox) findViewById(R.id.cb_reigst);
        mTvState = (TextView) findViewById(R.id.tv_state);
        mBtnRegist = findViewById(R.id.btn_regist_commit);
        mBtnRegist.setOnClickListener(this);
        mTvBacklogin = (TextView) findViewById(R.id.btn_back_login);
        mTvBacklogin.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTvState.setText(Html.fromHtml(UtilsUi.getString(R.string.label_declare)));
        mTvBacklogin.setText(Html.fromHtml(UtilsUi.getString(R.string.label_back_login)));
    }

    private void doRegist() {
        String userName = mRegistUsername.getText().toString().trim();
        String password = mRegistPassword.getText().toString().trim();
        String confirm = mReigstConfirm.getText().toString().trim();
        boolean checked = mCheckRegist.isChecked();
        if (UtilsString.checkEmpty(mActivity, userName, R.string.toast_username_empty)
                || UtilsString.checkEmpty(mActivity, password, R.string.toast_password_empty)
                || UtilsString.checkEmpty(mActivity, confirm, R.string.toast_password_confirm)) {
            return;
        }
        if (UtilsString.matchString(UtilsString.CHINESEREGEX, password)) {
            AppToast.show(mActivity, R.string.toast_password_chiness);
            return;
        }
        if (!checked) {
            AppToast.show(mActivity, R.string.toast_agree_protocol);
            return;
        }
        RegisteRequest request = new RegisteRequest();
        request.setUsername(userName);
        request.setPassword(password);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).registRequest(request);
        RetrofitUtils.request(mActivity, ob, mBtnRegist,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse registeResponse) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.USER_NAME, request.getUsername());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist_commit:
                doRegist();
                break;
            case R.id.btn_back_login:
                JumpManager.doJumpBack(mActivity);
                break;
        }
    }
}
