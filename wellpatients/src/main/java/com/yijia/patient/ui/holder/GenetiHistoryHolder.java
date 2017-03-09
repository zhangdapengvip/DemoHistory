package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.protocol.response.GeneapplyPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/4/28.
 * 基因检测推荐历史
 */
public class GenetiHistoryHolder extends AppBaseHolder<GeneapplyPageResponse.PageBean.RowsBean> {
    private TextView tvName;
    private TextView tvSex;
    private TextView tvPhone;

    public GenetiHistoryHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_geneti_history;
    }

    @Override
    public void refreshView(int position) {
        GeneapplyPageResponse.PageBean.RowsBean data = getData();
        tvName.setText(data.getName());
        tvSex.setText(Constants.SEX_MAN.equals(data.getSex()) ? "男" : "女");
        tvPhone.setText(data.getPhone());
    }


    @Override
    public void initView(View view) {
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSex = (TextView) view.findViewById(R.id.tv_sex);
        tvPhone = (TextView) view.findViewById(R.id.tv_phone);
    }
}
