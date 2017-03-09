package com.yijia.zkl.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.protocol.response.QandaPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/3/11.
 * 问答列表
 */
public class ExpertOnlineHolder extends AppBaseHolder<QandaPageResponse.Page.Rows> {

    private TextView mTvAsk;
    private TextView mTvAnswer;

    public ExpertOnlineHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_expert_online;
    }

    @Override
    public void initView(View view) {
        mTvAsk = (TextView) view.findViewById(R.id.tv_expert_ask);
        mTvAnswer = (TextView) view.findViewById(R.id.tv_expert_answer);
    }

    @Override
    public void refreshView(int position) {
        QandaPageResponse.Page.Rows data = getData();
        mTvAsk.setText(mActivity.getString(R.string.string_expert_ask, data.getQuestion()));
        mTvAnswer.setText(mActivity.getString(R.string.string_expert_answer, data.getAnswer()));
    }
}
