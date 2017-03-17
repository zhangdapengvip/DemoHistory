package com.zero.library.base.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.zero.library.base.uibase.AppBaseFragment;

import java.util.List;

public class FragmentManager implements RadioGroup.OnCheckedChangeListener {
    private List<AppBaseFragment> mFragments;
    private RadioGroup mRadioGroup;
    private FragmentActivity mOwner;
    private int mContainerId;
    private int currentTab;
    private OnTabChangedListener onTabChangedListener;

    public FragmentManager(FragmentActivity fragmentActivity,
                           List<AppBaseFragment> fragments, int fragmentContentId, RadioGroup rgs) {
        this.mFragments = fragments;
        this.mRadioGroup = rgs;
        this.mOwner = fragmentActivity;
        this.mContainerId = fragmentContentId;
        if (!fragments.get(0).isAdded()) {
            FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
            ft.add(fragmentContentId, fragments.get(0));
            ft.commit();
        }
        rgs.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            if (mRadioGroup.getChildAt(i).getId() == checkedId) {
                Fragment fragment = mFragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction();
                getCurrentFragment().onPause();
                if (fragment.isAdded()) {
                    fragment.onResume();
                } else {
                    ft.add(mContainerId, fragment);
                }
                showTab(i);
                ft.commit();
                if (null != onTabChangedListener) {
                    onTabChangedListener.OnTabCheckedChanged(radioGroup, checkedId, i);
                }
            }
        }
    }

    private void showTab(int idx) {
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction();
            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx;
    }

    /**
     * 获取一个带动画的FragmentTransaction
     */
    private FragmentTransaction obtainFragmentTransaction() {
        FragmentTransaction ft = mOwner.getSupportFragmentManager()
                .beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        // ft.setCustomAnimations(android.R.anim.fade_out,
        // android.R.anim.fade_in);
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(currentTab);
    }

    public OnTabChangedListener getOnTabChangedListener() {
        return onTabChangedListener;
    }

    public void setOnTabChangedListener(
            OnTabChangedListener onTabChangedListener) {
        this.onTabChangedListener = onTabChangedListener;
    }

    public interface OnTabChangedListener {
        void OnTabCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
    }

    public void addFragment(Fragment fm, boolean isfirst) {
        FragmentTransaction ft = mOwner.getSupportFragmentManager().beginTransaction();
        if (isfirst) {
            ft.add(mContainerId, fm);
        } else {
            ft.replace(mContainerId, fm);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public void addFragment(Fragment fm, String tag, boolean isfirst) {
        FragmentTransaction ft = mOwner.getSupportFragmentManager().beginTransaction();
        if (isfirst) {
            ft.add(mContainerId, fm);
        } else {
            ft.replace(mContainerId, fm, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public void addFragmentWithoutStack(Fragment oldfm, Fragment newfm) {
        FragmentTransaction ft = mOwner.getSupportFragmentManager().beginTransaction();
        removeFragment();
        ft.replace(mContainerId, newfm);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void removeFragment() {
        android.support.v4.app.FragmentManager fm = mOwner.getSupportFragmentManager();
        fm.popBackStack();
    }

    public Fragment getFragmentByTag(String tag) {
        android.support.v4.app.FragmentManager fm = mOwner.getSupportFragmentManager();
        Fragment frag = fm.findFragmentByTag(tag);
        return frag;
    }
}