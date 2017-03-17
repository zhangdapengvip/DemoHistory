package com.yijia.zkl.ui.fragment.homechild;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.activity.GenetiHistoryActivitiy;
import com.yijia.zkl.ui.activity.LaunchRecruitActivity;
import com.yijia.zkl.ui.activity.MineActiviity;
import com.yijia.zkl.ui.activity.MineHelpActivity;
import com.yijia.zkl.ui.factory.FragmentFactory;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.debug.DebugAppInfoActivity;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.view.TitleBarView;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/10.
 * 特需
 */
public class SpecialNeedFragment extends HomeBaseFragment {

    private TitleBarView mTitleBar;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.title_home_concur);
        mTitleBar.setRightText(R.string.title_right_recommend);
        mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));
        mTitleBar.setRightListener(v -> startGeneticHistoryActivity());
        mTitleBar.openDebug(() -> JumpManager.doJumpForward(mActivity, new Intent(mActivity, DebugAppInfoActivity
                .class)));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mTitleBar.setRightText(R.string.title_right_recommend);
                    mTitleBar.setRightListener(v -> startGeneticHistoryActivity());
                } else if (position == 1) {
                    mTitleBar.setRightText(R.string.title_right_history);
                    mTitleBar.setRightListener(v -> startHistoryActivity());
                } else if (position == 2) {
                    mTitleBar.setRightText(R.string.title_right_start);
                    mTitleBar.setRightListener(v -> startRecruitActivity());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mTitleBar) {
            LoginResponse loginInfo = UserManager.getLoginInfo();
            mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
        }
    }

    private void startGeneticHistoryActivity() {
        JumpManager.doJumpForward(mActivity, new Intent(mActivity, GenetiHistoryActivitiy.class));
    }

    private void startHistoryActivity() {
        JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineHelpActivity.class));
    }


    private void startRecruitActivity() {
        JumpManager.doJumpForward(mActivity, new Intent(mActivity, LaunchRecruitActivity.class));
    }


    @Override
    public List<AppPagerFragment> getFragments() {
        return FragmentFactory.createSpecialNeedFragments();
    }


    @Override
    protected void initData() {

    }
}
