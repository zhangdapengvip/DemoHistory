package com.yijia.zkl.ui.share;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.zero.library.base.uibase.AppBaseHolder;


public class ShareHolder extends AppBaseHolder<ShowInfo> {

    private TextView mShareTitle;
    private ImageView mShareImg;

    public ShareHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.item_share_content;
    }

    @Override
    public void initView(View view) {
        mShareImg = (ImageView) view.findViewById(R.id.share_img);
        mShareTitle = (TextView) view.findViewById(R.id.share_title);
    }

    @Override
    public void refreshView(int position) {
        ShowInfo data = getData();
        mShareImg.setBackgroundResource(data.icRes);
        mShareTitle.setText(data.title);
    }
}
