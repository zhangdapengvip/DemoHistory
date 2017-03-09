package com.yijia.zkl.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.bean.OfficeInfo;
import com.zero.library.base.uibase.AppBaseHolder;

/**
 * Created by ZeroAries on 2016/3/11.
 * 下拉列表
 */
public class PopuWindowHolder extends AppBaseHolder<OfficeInfo> {

    private TextView mTextView;

    public PopuWindowHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.item_field_choice;
    }

    @Override
    public void refreshView(int position) {
        mTextView.setText(getData().fullName);
    }

    @Override
    public void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.tv_field_title);

    }
}
