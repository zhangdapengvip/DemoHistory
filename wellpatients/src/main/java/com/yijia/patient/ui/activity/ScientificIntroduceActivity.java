package com.yijia.patient.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.JoinRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.DefaultActivity;
import com.yijia.patient.ui.protocol.response.PageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppEmptyDialog;
import com.zero.library.base.view.AppSpinner;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/22.
 * 科研介绍
 */
public class ScientificIntroduceActivity extends DefaultActivity {

    private AppSpinner asBaseType;
    private AppSpinner asBaseSex;
    private AppSpinner asBaseMarriage;
    private EditText etBaseIndication;
    private TextView tvBaseStarttime;
    private TextView tvBaseEndtime;
    private EditText etTestObjective;
    private AppSpinner asTestType;
    private EditText etTestType;
    private EditText etTestNumber;
    private EditText etTestMethod;
    private EditText etJoinCriteria;
    private EditText etUnjoinCriteria;
    private EditText etRelevantOrganization;
    private EditText etRelevantName;
    private EditText etRelevantContactway;
    private TextView mTvCommit;
    private LoginResponse mLoginInfo;
    private PageResponse.Page.Rows mPageInfo;
    private Dialog mNoticeDialog;

    @Override
    public int getResLayout() {
        return R.layout.activity_launch_recruit;
    }

    @Override
    public void initView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_scientific_introduce);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        asBaseType = (AppSpinner) findViewById(R.id.as_base_type);
        asBaseSex = (AppSpinner) findViewById(R.id.as_base_sex);
        asBaseMarriage = (AppSpinner) findViewById(R.id.as_base_marriage);
        etBaseIndication = (EditText) findViewById(R.id.et_base_indication);
        tvBaseStarttime = (TextView) findViewById(R.id.tv_base_starttime);
        tvBaseEndtime = (TextView) findViewById(R.id.tv_base_endtime);
        etTestObjective = (EditText) findViewById(R.id.et_test_objective);
        asTestType = (AppSpinner) findViewById(R.id.as_test_type);
        etTestType = (EditText) findViewById(R.id.et_test_type);
        etTestNumber = (EditText) findViewById(R.id.et_test_number);
        etTestMethod = (EditText) findViewById(R.id.et_test_method);
        etJoinCriteria = (EditText) findViewById(R.id.et_join_criteria);
        etUnjoinCriteria = (EditText) findViewById(R.id.et_unjoin_criteria);
        etRelevantOrganization = (EditText) findViewById(R.id.et_relevant_organization);
        etRelevantName = (EditText) findViewById(R.id.et_relevant_name);
        etRelevantContactway = (EditText) findViewById(R.id.et_relevant_contactway);
        unableView();
        mTvCommit = (TextView) findViewById(R.id.tv_commit);
        mTvCommit.setText(R.string.btn_join);
        mTvCommit.setOnClickListener(v -> {
            mNoticeDialog = UtilsUi.getNoticeDialog(mActivity, UtilsUi.getString(R.string.dialog_title_join)
                    , UtilsUi.getString(R.string.dialog_message_join)
                    , UtilsUi.getString(R.string.dialog_btn_join)
                    , UtilsUi.getString(R.string.dialog_btn_notjoin)
                    , (dialog, view) -> {
                        if (AppEmptyDialog.BtnView.LEFT == view) {
                            commitInfo();
                        }
                        dialog.dismiss();
                    });
            mNoticeDialog.show();
        });

    }

    private void unableView() {
        asBaseType.setEnabled(false);
        asBaseSex.setEnabled(false);
        asBaseMarriage.setEnabled(false);
        etBaseIndication.setEnabled(false);
        tvBaseStarttime.setEnabled(false);
        tvBaseEndtime.setEnabled(false);
        etTestObjective.setEnabled(false);
        asTestType.setEnabled(false);
        etTestType.setEnabled(false);
        etTestNumber.setEnabled(false);
        etTestMethod.setEnabled(false);
        etJoinCriteria.setEnabled(false);
        etUnjoinCriteria.setEnabled(false);
        etRelevantOrganization.setEnabled(false);
        etRelevantName.setEnabled(false);
        etRelevantName.setEnabled(false);
        etRelevantContactway.setEnabled(false);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mPageInfo = (PageResponse.Page.Rows) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mPageInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        asBaseType.setList(Constants.getRecruitType());
        asBaseSex.setList(Constants.getRecruitSex());
        asBaseMarriage.setList(Constants.getRecruitMarriage());
        asTestType.setList(Constants.getRecruitTestType());

        asBaseType.setKey(mPageInfo.getTextType());
        asBaseSex.setKey(mPageInfo.getTextSex());
        asBaseMarriage.setKey(mPageInfo.getMarriageState());
        etBaseIndication.setText(mPageInfo.getDrugIndication());
        tvBaseStarttime.setText(mPageInfo.getStartDate());
        tvBaseEndtime.setText(mPageInfo.getOverDate());
        etTestObjective.setText(mPageInfo.getTextTarget());
        asTestType.setKey(mPageInfo.getTextClassification());
        etTestType.setText(mPageInfo.getDesignType());
        etTestNumber.setText(mPageInfo.getTextNumber());
        etTestMethod.setText(mPageInfo.getTextMethod());
        etJoinCriteria.setText(mPageInfo.getContext());
        etUnjoinCriteria.setText(mPageInfo.getJoinRequire());
        etRelevantOrganization.setText(mPageInfo.getLaunchOrg());
        etRelevantName.setText(mPageInfo.getLaunchName());
        etRelevantContactway.setText(mPageInfo.getLaunchLink());
    }

    private void commitInfo() {
        mLoginInfo = UserManager.getLoginInfo();
        JoinRequest request = new JoinRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setPkResearchProject(mPageInfo.getPkResearchproject());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).joinRequest(request);
        RetrofitUtils.request(mActivity, ob, mTvCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, response.getMessage());
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
}
