package com.yijia.patient.ui.fragment.viewdetail;

import android.view.View;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.ViewPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/3/28.
 * 本期简介
 */
public class BriefIntroduceFragment extends AppPagerFragment {

    private TextView mContent;
    private ViewPageResponse.Page.Rows mViewInfo;

    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_brief_introduce);
    }

    @Override
    protected int getResLayout() {
        return R.layout.activity_brief_introduce;
    }

    @Override
    protected void initView(View view) {
        mContent = (TextView) view.findViewById(R.id.tv_content);
    }

    @Override
    protected void initData() {
        mViewInfo = (ViewPageResponse.Page.Rows) mArguments.getSerializable(AppConstants.PARCELABLE_KEY);
        mContent.setText(mViewInfo.getAbout());
    }
}
