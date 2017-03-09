package com.healthsoulmate.zkl.forum.factory;

import android.os.Bundle;

import com.healthsoulmate.zkl.forum.fragment.ForumCollectFragment;
import com.healthsoulmate.zkl.forum.fragment.ForumHistoryFragment;
import com.healthsoulmate.zkl.forum.fragment.ForumListFragment;
import com.healthsoulmate.zkl.forum.fragment.PlateFragment;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.uibase.AppPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/4/29.
 * Fragment工厂类
 */
public class FragmentFactory {
    private static ArrayList<AppPagerFragment> mHomeFragments = new ArrayList<>();


    public static List<AppPagerFragment> createHomeFragments() {
        mHomeFragments.clear();
        mHomeFragments.add(new PlateFragment());
        mHomeFragments.add(new ForumHistoryFragment());
        return mHomeFragments;
    }

    private static ArrayList<AppBaseFragment> mForumFragments = new ArrayList<>();


    public static List<AppBaseFragment> createForumFragments(List<DiscuzsectionPageResponse.ListBean> itemList) {
        mForumFragments.clear();
        mForumFragments.add(new ForumCollectFragment());
        for (DiscuzsectionPageResponse.ListBean item : itemList) {
            ForumListFragment fragment = new ForumListFragment();
            Bundle argument = new Bundle();
            argument.putParcelable(AppConstants.PARCELABLE_KEY, item);
            fragment.setArguments(argument);
            mForumFragments.add(fragment);
        }
        return mForumFragments;
    }
}
