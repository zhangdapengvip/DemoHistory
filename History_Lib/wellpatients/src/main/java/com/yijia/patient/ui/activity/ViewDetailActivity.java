package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.umeng.socialize.UMShareAPI;
import com.yijia.patient.R;
import com.yijia.patient.ui.factory.FragmentFactory;
import com.yijia.patient.ui.protocol.response.ViewPageResponse;
import com.yijia.patient.ui.share.ShareHelper;
import com.yijia.patient.ui.share.ShareInfo;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.manager.PropertyManager;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.AppPagerFragmentAdapter;
import com.zero.library.base.uibase.WebViewVideoBaseFragmentActivity;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.CustomViewPager;
import com.zero.library.base.view.Indicator.TabPageIndicator;
import com.zero.library.base.view.TitleBarView;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/28.
 * 视野详情
 */
public class ViewDetailActivity extends WebViewVideoBaseFragmentActivity {
    private UMShareAPI mShareAPI = null;
    protected TabPageIndicator mTabIndicator;
    protected CustomViewPager mViewPager;
    private List<AppPagerFragment> mFragments;
    private ViewPageResponse.Page.Rows mViewInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_view_detail;
    }

    @Override
    protected void initChildView() {
        mShareAPI = UMShareAPI.get(mContext);
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_view_detail);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        titleBar.setRightIcon(R.drawable.ic_right_share);
        titleBar.setRightListener(v -> startShare());
        Intent intent = getIntent();
        String title = intent.getStringExtra(AppConstants.EXTRA_TITLE);
        titleBar.setTitle(title);
        mViewInfo = (ViewPageResponse.Page.Rows) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mViewInfo) {
            JumpManager.doJumpBack(mActivity);
            return;
        }
        mFragments = FragmentFactory.createViewDetailFragments(mViewInfo);
        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        mViewPager.setEnableScroll(true);
        mViewPager.setAdapter(new AppPagerFragmentAdapter(
                getSupportFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabIndicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        mTabIndicator.setViewPager(mViewPager);
        mTabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragments.get(position).onSelected();
                for (int index = 0; index < mFragments.size(); index++) {
                    mFragments.get(index).setShowing(index == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabIndicator.onPageSelected(0);
        loadUrl(PropertyManager.getRequestHost() + "video?url=" + mViewInfo.getVideourl());
    }

    private void startShare() {
        ShareHelper helper = new ShareHelper(mActivity);
        ShareInfo info = new ShareInfo();
        info.title = UtilsUi.getString(R.string.title_share);
        info.content = mViewInfo.getTitle();
        info.imgUrl = UtilsString.getEncodeUrl("/", mViewInfo.getImage());
        info.openUrl = PropertyManager.getRequestHost() + "video?url=" + mViewInfo.getVideourl();
        helper.share(info);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mFragments) mFragments.clear();
    }
}