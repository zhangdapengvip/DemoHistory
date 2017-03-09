package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.protocol.response.ViewPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.picasso.UtilsPicasso;

/**
 * Created by ZeroAries on 2016/3/11.
 * 视野列表条目
 */
public class ViewNewsHolder extends AppBaseHolder<ViewPageResponse.Page.Rows> {

    private ImageView mNewsImg;
    private TextView mtitle;
    private TextView mName;
    private TextView mDate;

    public ViewNewsHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_view_news;
    }

    @Override
    public void refreshView(int position) {
        ViewPageResponse.Page.Rows data = getData();
        UtilsPicasso.loadCircleImage(mActivity, data.getImage(),
                R.drawable.ic_default_news, mNewsImg,
                R.dimen.dimen_60);
        mtitle.setText(data.getTitle());
        mName.setText(data.getName());
        mDate.setText(data.getReleasedate());
    }

    @Override
    public void initView(View view) {
        mNewsImg = (ImageView) view.findViewById(R.id.iv_news);
        mtitle = (TextView) view.findViewById(R.id.tv_title);
        mName = (TextView) view.findViewById(R.id.tv_name);
        mDate = (TextView) view.findViewById(R.id.tv_date);
    }
}
