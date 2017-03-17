package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.HelpGetResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/3/11.
 * 求助历史
 */
public class HelpHistroyHolder extends AppBaseHolder<HelpGetResponse.Datas> {

    private TextView mTvTitle;
    private TextView mTvContent;

    public HelpHistroyHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_help_history;
    }

    @Override
    public void refreshView(int position) {
        HelpGetResponse.Datas data = getData();
        mTvTitle.setText(data.getTitle());
        mTvContent.setText(data.getComment());
    }

    @Override
    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
    }
}
