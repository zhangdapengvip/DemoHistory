package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.FamilydiseaseinfoListResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/6/24.
 * 家庭疾病史
 */
public class FamilyDiseaseHistoryHolder extends AppBaseHolder<FamilydiseaseinfoListResponse.ListBean> {

    private TextView tvDiseasename;
    private TextView tvMember;
    private TextView tvAge;
    private TextView tvContent;

    public FamilyDiseaseHistoryHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_family_disease_history;
    }

    @Override
    public void refreshView(int position) {
        FamilydiseaseinfoListResponse.ListBean data = getData();
        tvDiseasename.setText(data.getDiseasename());
        tvMember.setText(data.getMember());
        tvAge.setText(data.getAge());
        tvContent.setText(data.getContent());
    }

    @Override
    public void initView(View view) {
        tvDiseasename = (TextView) view.findViewById(R.id.tv_diseasename);
        tvMember = (TextView) view.findViewById(R.id.tv_member);
        tvAge = (TextView) view.findViewById(R.id.tv_age);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
    }
}
