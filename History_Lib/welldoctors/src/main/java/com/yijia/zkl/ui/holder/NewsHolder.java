package com.yijia.zkl.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.protocol.response.NewsPage;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.picasso.UtilsPicasso;

/**
 * Created by ZeroAries on 2016/3/11.
 * 资讯列表
 */
public class NewsHolder extends AppBaseHolder<NewsPage> {

    private TextView mTvDate;
    private ImageView mIvNews;
    private TextView mTvTitle;
    private TextView mTvReadCount;

    public NewsHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_news;
    }

    @Override
    public void refreshView(int position) {
        NewsPage data = getData();
        UtilsPicasso.loadRoundImage(mActivity, data.getPictureurl(),
                R.drawable.ic_default_news, mIvNews,
                R.dimen.dimen_60, 15);
        mTvTitle.setText(data.getTitle());
        mTvDate.setText(data.getReleasedate());
        mTvReadCount.setText(mActivity.getString(R.string.label_read_count, data.getReadnum()));
    }

    @Override
    public void initView(View view) {
        mIvNews = (ImageView) view.findViewById(R.id.iv_news);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvReadCount = (TextView) view.findViewById(R.id.tv_read_count);
    }
}
