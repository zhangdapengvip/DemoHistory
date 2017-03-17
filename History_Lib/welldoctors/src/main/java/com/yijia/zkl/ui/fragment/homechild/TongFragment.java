package com.yijia.zkl.ui.fragment.homechild;

import android.content.Intent;
import android.view.View;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.activity.MineActiviity;
import com.yijia.zkl.ui.factory.FragmentFactory;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.view.TitleBarView;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/10.
 * 名医堂
 */
public class TongFragment extends HomeBaseFragment {
    private TitleBarView mTitleBar;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.title_home_tong);
        mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mTitleBar) {
            LoginResponse loginInfo = UserManager.getLoginInfo();
            mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
        }
    }

    @Override
    public List<AppPagerFragment> getFragments() {
        return FragmentFactory.createTongFragments();
    }

    @Override
    protected void initData() {

    }
}
