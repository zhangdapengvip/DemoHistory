package com.yijia.zkl.ui.fragment.homechild;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.AppPagerFragmentAdapter;
import com.zero.library.base.view.CustomViewPager;
import com.zero.library.base.view.Indicator.TabPageIndicator;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/11.
 * 名医堂
 */
public abstract class HomeBaseFragment extends AppBaseFragment {

    protected TabPageIndicator mTabIndicator;
    protected CustomViewPager mViewPager;
    protected LoginResponse mLoginInfo;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_base_home;
    }

    @Override
    protected void initView(View view) {
        mLoginInfo = UserManager.getLoginInfo();
        mViewPager = (CustomViewPager) view.findViewById(R.id.pager);
        mViewPager.setEnableScroll(true);
        mViewPager.setAdapter(new AppPagerFragmentAdapter(
                getChildFragmentManager(), getFragments()));
        mViewPager.setOffscreenPageLimit(getFragments().size());
        mTabIndicator = (TabPageIndicator) view
                .findViewById(R.id.tab_indicator);
        mTabIndicator.setViewPager(mViewPager);
        mTabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getFragments().get(position).onSelected();
                for (int index = 0; index < getFragments().size(); index++) {
                    getFragments().get(index).setShowing(index == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabIndicator.onPageSelected(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoginInfo = UserManager.getLoginInfo();
    }

    public abstract List<AppPagerFragment> getFragments();

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragments().clear();
    }
}
