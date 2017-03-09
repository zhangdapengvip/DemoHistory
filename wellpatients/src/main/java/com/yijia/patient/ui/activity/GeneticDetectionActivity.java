package com.yijia.patient.ui.activity;

import android.content.Intent;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.GenetestPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.WebViewBaseActivity;
import com.zero.library.base.view.TitleBarView;

/**
 * Created by ZeroAries on 2016/4/26.
 * 基因检测
 */
public class GeneticDetectionActivity extends WebViewBaseActivity {

    private GenetestPageResponse.PageBean.RowsBean mGeneticInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_genetic_detection;
    }

    @Override
    protected void initChildView() {
        Intent intent = getIntent();
        mGeneticInfo = (GenetestPageResponse.PageBean.RowsBean) intent.getSerializableExtra(
                AppConstants.PARCELABLE_KEY);
        if (null == mGeneticInfo) JumpManager.doJumpBack(mActivity);
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(mGeneticInfo.getTitle());
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        findViewById(R.id.btn_detailed_understanding).setOnClickListener(v -> {
            Intent jumpIntent = new Intent(mActivity, DetailedUnderstandingActivity.class);
            jumpIntent.putExtra(AppConstants.PARCELABLE_KEY, mGeneticInfo);
            JumpManager.doJumpForward(mActivity, jumpIntent);
        });
        loadUrl(mGeneticInfo.getContenturl());
    }
}