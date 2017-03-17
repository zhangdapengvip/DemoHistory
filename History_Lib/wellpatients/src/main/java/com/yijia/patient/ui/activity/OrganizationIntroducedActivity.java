package com.yijia.patient.ui.activity;

import android.content.Intent;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.HospitalPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.uibase.WebViewBaseActivity;
import com.zero.library.base.view.TitleBarView;

/**
 * Created by ZeroAries on 2016/3/17.
 * 特需——机构介绍
 */
public class OrganizationIntroducedActivity extends WebViewBaseActivity {

    private HospitalPageResponse.Page.Rows mContentInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_organization_introduce;
    }

    @Override
    protected void initChildView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_organization_introduce);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        findViewById(R.id.tv_ask_help).setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, PatientInformationActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, mContentInfo);
            JumpManager.doJumpForward(mActivity, intent);
        });

        Intent intent = getIntent();
        mContentInfo = (HospitalPageResponse.Page.Rows) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mContentInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        loadUrl(mContentInfo.getAboutPageUrl());
    }
}