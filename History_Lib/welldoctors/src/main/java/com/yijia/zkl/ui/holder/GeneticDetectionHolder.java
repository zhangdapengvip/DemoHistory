package com.yijia.zkl.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.activity.DetailedUnderstandingActivity;
import com.yijia.zkl.ui.protocol.response.GenetestPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.picasso.UtilsPicasso;

/**
 * Created by ZeroAries on 2016/3/11.
 * 医疗援助列表
 */
public class GeneticDetectionHolder extends AppBaseHolder<GenetestPageResponse.PageBean.RowsBean> {


    private TextView mTitle;
    private TextView mContent;
    private TextView mRecommend;
    private ImageView mIvNews;

    public GeneticDetectionHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_genetic_detection;
    }

    @Override
    public void refreshView(int position) {
        GenetestPageResponse.PageBean.RowsBean data = getData();
        UtilsPicasso.loadRoundImage(mActivity, data.getImageurl(),
                R.drawable.ic_default_news, mIvNews,
                R.dimen.dimen_60, 15);
        mTitle.setText(data.getTitle());
        mRecommend.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, DetailedUnderstandingActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, data);
            JumpManager.doJumpForward(mActivity, intent);
        });

    }

    @Override
    public void initView(View view) {
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mContent = (TextView) view.findViewById(R.id.tv_content);
        mRecommend = (TextView) view.findViewById(R.id.tv_recommend);
        mIvNews = (ImageView) view.findViewById(R.id.iv_news);
    }
}