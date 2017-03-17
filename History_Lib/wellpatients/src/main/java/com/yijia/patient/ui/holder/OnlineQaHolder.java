package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.protocol.response.OnlineqaPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.picasso.UtilsPicasso;

/**
 * Created by ZeroAries on 2016/3/11.
 * 专家在线条目
 */
public class OnlineQaHolder extends AppBaseHolder<OnlineqaPageResponse.Page.Rows> {

    private ImageView mNewsImg;
    private TextView mContent;
    private TextView mTitle;
    private TextView mTime;
    private TextView mName;
    private TextView mDate;
    private TextView mOnlineIng;

    public OnlineQaHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_online_qa;
    }

    @Override
    public void refreshView(int position) {
        OnlineqaPageResponse.Page.Rows data = getData();
        UtilsPicasso.loadCircleImage(mActivity, data.getImage(),
                R.drawable.ic_default_news, mNewsImg,
                R.dimen.dimen_60);
        mName.setText(data.getName());
        mDate.setText(data.getBegindate());
        mTime.setText(data.getBegintime() + "-" + data.getEndtime());
        mContent.setText(data.getTitle());
        mTitle.setText(Constants.getTitleMap().get(data.getZhicheng()));
        boolean isToday = data.getBegindate().equals(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_YEAR));
        boolean isStart = data.getBegintime().compareTo(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_HOUR)) < 0;
        boolean isEnd = data.getEndtime().compareTo(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_HOUR)) > 0;
        if (isToday && isStart && isEnd) {
            mOnlineIng.setVisibility(View.VISIBLE);
        } else {
            mOnlineIng.setVisibility(View.GONE);
        }
    }

    @Override
    public void initView(View view) {
        mNewsImg = (ImageView) view.findViewById(R.id.iv_head);
        mContent = (TextView) view.findViewById(R.id.tv_cotent);
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mName = (TextView) view.findViewById(R.id.tv_name);
        mDate = (TextView) view.findViewById(R.id.tv_date);
        mTime = (TextView) view.findViewById(R.id.tv_time);
        mOnlineIng = (TextView) view.findViewById(R.id.tv_online_ing);
    }
}
