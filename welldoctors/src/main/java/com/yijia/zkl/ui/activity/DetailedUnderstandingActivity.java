package com.yijia.zkl.ui.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.bean.Constants;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.GeneapplySaveRequest;
import com.yijia.zkl.ui.protocol.response.GenetestPageResponse;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/26.
 * 基因检测申请
 */
public class DetailedUnderstandingActivity extends DefaultActivity {

    private GenetestPageResponse.PageBean.RowsBean mGeneticInfo;
    private EditText etName;
    private RadioButton cbSexMan;
    private EditText etAge;
    private EditText etPhone;
    private TextView tvCommit;
    private AppAlertDialog mNoticeDialog;

    @Override
    public int getResLayout() {
        return R.layout.activity_detaile_understanding;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mGeneticInfo = (GenetestPageResponse.PageBean.RowsBean) intent.getSerializableExtra(
                AppConstants.PARCELABLE_KEY);
        if (null == mGeneticInfo) JumpManager.doJumpBack(mActivity);
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle("");
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));

        etName = (EditText) findViewById(R.id.et_name);
        cbSexMan = (RadioButton) findViewById(R.id.cb_sex_man);
        etAge = (EditText) findViewById(R.id.et_age);
        etPhone = (EditText) findViewById(R.id.et_phone);

        tvCommit = (TextView) findViewById(R.id.tv_commit);
        tvCommit.setOnClickListener(v -> commit());
    }

    private void commit() {
        String name = etName.getText().toString().trim();
        String sex = cbSexMan.isChecked() ? Constants.SEX_MAN : Constants.SEX_WOMAN;
        String age = etAge.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, sex, R.string.hint_choice_sex) ||
                UtilsString.checkEmpty(mActivity, etName) ||
                UtilsString.checkEmpty(mActivity, etAge) ||
                UtilsString.checkRegex(mActivity, phone, UtilsString.PHONEEGEX, R.string.hint_phone)) {
            return;
        }
        if (Integer.valueOf(age) > Constants.MAX_AGE) {
            AppToast.show(mActivity, R.string.toast_input_true_age);
            return;
        }
        LoginResponse loginResponse = UserManager.getLoginInfo();
        GeneapplySaveRequest request = new GeneapplySaveRequest();
        request.setPkUser(loginResponse.getDatas().getPkUser());
        request.setPkGenetest(mGeneticInfo.getPkGenetest());
        request.setName(name);
        request.setAge(age);
        request.setSex(sex);
        request.setPhone(phone);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).geneapplySaveRequest(request);
        RetrofitUtils.request(mActivity, ob, tvCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        mNoticeDialog = UtilsUi.getNoticeDialog(mActivity, UtilsUi.getString(R.string
                                .dialog_recommend_success), (dialog, view) -> JumpManager.doJumpBack(mActivity));
                        mNoticeDialog.show();
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
    protected void onDestroy() {
        super.onDestroy();
        if (null != mNoticeDialog) mNoticeDialog.dismiss();
    }

    @Override
    public void initData() {

    }
}