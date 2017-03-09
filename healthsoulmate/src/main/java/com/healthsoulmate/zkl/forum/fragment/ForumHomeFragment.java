package com.healthsoulmate.zkl.forum.fragment;

import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityForumTabBinding;
import com.healthsoulmate.zkl.forum.factory.FragmentFactory;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.AppPagerFragmentAdapter;
import com.zero.library.base.view.CustomViewPager;
import com.zero.library.base.view.Indicator.TabPageIndicator;

import java.util.List;

/**
 * Created by ZeroAries on 2016/4/27.
 * 论坛首页
 */
public class ForumHomeFragment extends AppBaseFragment {
    protected TabPageIndicator mTabIndicator;
    protected CustomViewPager mViewPager;
    private ActivityForumTabBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_forum_tab;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mBinding = (ActivityForumTabBinding) inflate;
        mBinding.titleBar.setTitle(R.string.tab_forum);
        mViewPager = mBinding.forumPager;
        mTabIndicator = mBinding.tabIndicator;
        mViewPager.setEnableScroll(true);
        List<AppPagerFragment> fourmFragments = FragmentFactory.createHomeFragments();
        mViewPager.setAdapter(new AppPagerFragmentAdapter(getFragmentManager(), fourmFragments));
        mViewPager.setOffscreenPageLimit(fourmFragments.size());
        mTabIndicator.setViewPager(mViewPager);
        mTabIndicator.onPageSelected(0);
        mTabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fourmFragments.get(position).onSelected();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void initData() {

    }
}