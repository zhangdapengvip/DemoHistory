package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yijia.patient.R;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.uibase.DefaultPagerAdapter;
import com.zero.library.base.utils.UtilsSharedPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/4/7.
 * 引导页
 */
public class GuidePageActivity extends DefaultActivity {

    private ViewPager mViewPager;

    @Override
    public int getResLayout() {
        return R.layout.activity_guide_page;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
    }

    @Override
    public void initData() {
        List<Integer> pageList = new ArrayList<>();
        pageList.add(R.drawable.bg_guide_one);
        pageList.add(R.drawable.bg_guide_two);
        pageList.add(R.drawable.bg_guide_three);
        MyAdapter adapter = new MyAdapter(pageList);
        mViewPager.setAdapter(adapter);
    }

    private class MyAdapter extends DefaultPagerAdapter<Integer> {

        public MyAdapter(List<Integer> dataList) {
            super(dataList);
        }

        @Override
        protected View initItemView(Integer integer, int position) {
            View pageItem = View.inflate(mContext, R.layout.item_guide_page, null);
            ImageView imgContent = (ImageView) pageItem.findViewById(R.id.iv_content);
            imgContent.setBackgroundResource(integer);
            imgContent.setOnClickListener(v -> {
                if (position == getDataList().size() - 1) {
                    JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, LoginActivity.class));
                    SharedPreferences sp = UtilsSharedPreference.getSharedPreference(UtilsSharedPreference
                            .OTHER_SETTING);
                    sp.edit().putBoolean(WelcomeActivity.SAVE_TAG, true).commit();
                }
            });
            return pageItem;
        }
    }
}
