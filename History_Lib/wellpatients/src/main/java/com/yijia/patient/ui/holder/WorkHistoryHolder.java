package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.WorkinfoListResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/6/24.
 * 工作情况
 */
public class WorkHistoryHolder extends AppBaseHolder<WorkinfoListResponse.ListBean> {

    private TextView tvOccupation;
    private TextView tvPost;
    private TextView tvWorkinghours;
    private TextView tvWorkinghours1;


    public WorkHistoryHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_work_history;
    }

    @Override
    public void refreshView(int position) {
        WorkinfoListResponse.ListBean data = getData();
        tvOccupation.setText(data.getOccupation());
        tvPost.setText(data.getPost());
        tvWorkinghours.setText(data.getWorkinghours());
        tvWorkinghours1.setText(data.getWorkinghours1());
    }

    @Override
    public void initView(View view) {
        tvOccupation = (TextView) view.findViewById(R.id.tv_occupation);
        tvPost = (TextView) view.findViewById(R.id.tv_post);
        tvWorkinghours = (TextView) view.findViewById(R.id.tv_workinghours);
        tvWorkinghours1 = (TextView) view.findViewById(R.id.tv_workinghours1);
    }
}
