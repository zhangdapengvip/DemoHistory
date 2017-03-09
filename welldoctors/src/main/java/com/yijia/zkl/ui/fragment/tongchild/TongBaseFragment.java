package com.yijia.zkl.ui.fragment.tongchild;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.yijia.zkl.ui.bean.Constants;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.uibase.ListBaseFragment;

/**
 * Created by ZeroAries on 2016/3/10.
 * 名医堂Tab基类
 */
public abstract class TongBaseFragment extends ListBaseFragment {
    protected int mPageNum = AppConstants.FIRST_NUM;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRefreshList.getRefreshableView().setDivider(new BitmapDrawable());
    }

    @Override
    public void onSelected() {
        super.onSelected();
        if (null != mRefreshList) mRefreshList.perfectPullRefresh(Constants.REFRESH_TIME);
    }
}
