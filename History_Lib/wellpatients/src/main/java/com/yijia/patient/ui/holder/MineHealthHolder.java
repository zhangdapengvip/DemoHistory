package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.HealthyInfo;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/6/24.
 * 我的健康
 */
public class MineHealthHolder extends AppBaseHolder<HealthyInfo> {

    private TextView etHypotension;
    private TextView etHypertension;
    private TextView etBloodfat;
    private TextView etBloodsugar;

    public MineHealthHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_mine_health;
    }

    @Override
    public void refreshView(int position) {
        HealthyInfo data = getData();
        etHypotension.setText(data.getHypotension());
        etHypertension.setText(data.getHypertension());
        etBloodfat.setText(data.getBloodfat());
        etBloodsugar.setText(data.getBloodsugar());
    }

    @Override
    public void initView(View view) {
        etHypotension = (TextView) view.findViewById(R.id.tv_hypotension);
        etHypertension = (TextView) view.findViewById(R.id.tv_hypertension);
        etBloodfat = (TextView) view.findViewById(R.id.tv_bloodfat);
        etBloodsugar = (TextView) view.findViewById(R.id.tv_bloodsugar);
    }
}
