package com.yijia.patient.ui.fragment.specialneed;

import com.yijia.patient.ui.bean.Constants;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.uibase.ListBaseFragment;

/**
 * Created by ZeroAries on 2016/3/10.
 * 特需Tab基类
 */
public abstract class SpecialBaseFragment extends ListBaseFragment {
    protected int mPageNum = AppConstants.FIRST_NUM;

    @Override
    public void onSelected() {
        super.onSelected();
        if (null != mRefreshList) mRefreshList.perfectPullRefresh(Constants.REFRESH_TIME);
    }
}
