package com.yijia.zkl.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.protocol.response.InfoiscussPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.picasso.UtilsPicasso;

/**
 * Created by ZeroAries on 2016/3/11.
 * 咨询列表
 */
public class CommentHolder extends AppBaseHolder<InfoiscussPageResponse.Page.Rows> {

    private TextView mTvDate;
    private ImageView mIvHead;
    private TextView mTvName;
    private TextView mTvContent;

    public CommentHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_comment;
    }

    @Override
    public void refreshView(int position) {
        InfoiscussPageResponse.Page.Rows data = getData();
        UtilsPicasso.loadCircleImage(mActivity, data.getUserImage(),
                R.drawable.ic_default_news, mIvHead,
                R.dimen.dimen_60);
        mTvName.setText(data.getUserName());
        mTvDate.setText(data.getDiscusstime());
        mTvContent.setText(data.getContent());
    }

    @Override
    public void initView(View view) {
        mIvHead = (ImageView) view.findViewById(R.id.iv_head);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
    }
}
