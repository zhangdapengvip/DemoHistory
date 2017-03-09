package com.zero.library.base.uibase;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AppPagerFragmentAdapter extends FragmentPagerAdapter {
    protected List<AppPagerFragment> mFragments;

    public AppPagerFragmentAdapter(FragmentManager fm, List<AppPagerFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int location) {
        return mFragments.get(location);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getPagerTitle();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
