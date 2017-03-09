package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.PatientInformationActivity;
import com.yijia.patient.ui.protocol.response.HospitalPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.picasso.UtilsPicasso;

/**
 * Created by ZeroAries on 2016/3/11.
 * 医疗援助列表
 */
public class MedicalAssistanceHolder extends AppBaseHolder<HospitalPageResponse.Page.Rows> {


    private TextView mTitle;
    private TextView mContent;
    private ImageView mIvNews;
    private TextView mTvAskHelp;

    public MedicalAssistanceHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_medical_assistance;
    }

    @Override
    public void refreshView(int position) {
        HospitalPageResponse.Page.Rows data = getData();
        UtilsPicasso.loadRoundImage(mActivity, data.getPictureurl(),
                R.drawable.ic_default_news, mIvNews,
                R.dimen.dimen_60, 15);
        mTitle.setText(data.getHospitalname());
        mContent.setText(data.getAbout());
        mTvAskHelp.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, PatientInformationActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, data);
            JumpManager.doJumpForward(mActivity, intent);
        });
    }

    @Override
    public void initView(View view) {
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mContent = (TextView) view.findViewById(R.id.tv_content);
        mIvNews = (ImageView) view.findViewById(R.id.iv_news);
        mTvAskHelp = (TextView) view.findViewById(R.id.tv_ask_help);
    }
}
