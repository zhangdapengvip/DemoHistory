package com.yijia.patient.ui.deprecated;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.yijia.patient.R;
import com.zero.library.base.uibase.DefaultActivity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.JoinRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.PageResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.view.AppSpinner;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 特需——科研参与
 */
public class JoinRecruitActivity extends DefaultActivity {
    private EditText mUserName;
    private EditText mHospital;
    private EditText mOffice;
    private AppSpinner mTitle;
    private EditText mPhone;
    private PageResponse.Page.Rows mPageInfo;
    private LoginResponse mLoginInfo;
    private View mTvCommit;

    @Override
    public int getResLayout() {
        return R.layout.activity_join_recruit;
    }

    @Override
    public void initView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_fill_information);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mHospital = (EditText) findViewById(R.id.et_hospital);
        mOffice = (EditText) findViewById(R.id.et_office);
        mTitle = (AppSpinner) findViewById(R.id.spinner_title);
        mPhone = (EditText) findViewById(R.id.et_phone);
        mTvCommit = findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(v -> commitInfo());
        mTitle.setViewBackground(R.drawable.bg_transparent);
        mTitle.setIcon(R.drawable.ic_right_down);
    }

    private void commitInfo() {
        String userName = mUserName.getText().toString().trim();
        String hospital = mHospital.getText().toString().trim();
        String offcie = mOffice.getText().toString().trim();
        String title = mTitle.getCheckItem().getKey();
        String phone = mPhone.getText().toString().trim();
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
    public void initData() {
        Intent intent = getIntent();
        mPageInfo = (PageResponse.Page.Rows) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mPageInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        mLoginInfo = UserManager.getLoginInfo();
        mUserName.setText(mLoginInfo.getDatas().getName());
        mHospital.setText(mLoginInfo.getDatas().getHospital());
        mPhone.setText(mLoginInfo.getDatas().getPhone());
        mOffice.setText(mLoginInfo.getDatas().getDepartment());
        mTitle.setHintText(R.string.hint_title);
        mTitle.setList(Constants.getTitleList());
        mTitle.setMaxItemCount(6);
        mTitle.setKey(mLoginInfo.getDatas().getTitle());
    }
}
