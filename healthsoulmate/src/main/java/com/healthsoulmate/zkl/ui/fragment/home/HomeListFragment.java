package com.healthsoulmate.zkl.ui.fragment.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentHomeBinding;
import com.healthsoulmate.zkl.ui.activity.NewsDetailActivity;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.holder.HomeListHolder;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.InformationHeadRequest;
import com.healthsoulmate.zkl.ui.protocol.request.InformationPageRequest;
import com.healthsoulmate.zkl.ui.protocol.response.InformationHeadResponse;
import com.healthsoulmate.zkl.ui.protocol.response.InformationPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.NewsBean;
import com.healthsoulmate.zkl.ui.view.ScrollingViewPager;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.AppBaseListFragment;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsUi;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/20.
 * 首页
 */
public class HomeListFragment extends AppBaseListFragment {
    private final String SAVE_TAG = HomeListFragment.class.getSimpleName();

    private int mPageNum = AppConstants.FIRST_NUM;
    private ScrollingViewPager mAutoScrollView;
    private FragmentHomeBinding mBinding;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        super.initView(view, inflate);
        mBinding = (FragmentHomeBinding) inflate;
        mBinding.titleBar.setTitle(R.string.title_home);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<NewsBean>(mActivity, listView, AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new HomeListHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                requestList();
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestList();
        requestAdvert();
    }

    @Override
    protected void addHeaderView(ListView listView) {
        View headView = UtilsUi.inflate(mActivity, R.layout.wgt_head_auto_viewpager);
        mAutoScrollView = (ScrollingViewPager) headView.findViewById(R.id.vp_aotu_scroll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UtilsUi.getScreenWidth(mActivity), UtilsUi
                .getScreenWidth(mActivity) * 2 / 5);
        mAutoScrollView.setLayoutParams(params);
        if (listView.getHeaderViewsCount() == 0) listView.addHeaderView(headView);
    }

    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
    }


    private void requestAdvert() {
        InformationHeadRequest request = new InformationHeadRequest();
        Observable<InformationHeadResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .informationHeadRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<InformationHeadResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(InformationHeadResponse response) {
                        fillAdvert(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    private void requestList() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            InformationPageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_TAG), InformationPageResponse.class);
            if (null != response) fillList(response, false);
        }
        InformationPageRequest request = new InformationPageRequest();
        request.setType("0");
        request.setPage(String.valueOf(mPageNum));
        Observable<InformationPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .informationPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<InformationPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(InformationPageResponse response) {
                        if (mPageNum == AppConstants.FIRST_NUM)
                            saveInfo(SAVE_TAG, UtilsGson.toJson(response));
                        fillList(response, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.loadError();
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillList(InformationPageResponse response, boolean isNet) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        if (isNet) mPageNum++;
    }


    private void fillAdvert(InformationHeadResponse response) {
        mAutoScrollView.setOnPagerClickListener(position -> {
            NewsBean item = response.getPage().getRows().get(position);
            Intent intent = new Intent(mActivity, NewsDetailActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, item);
            JumpManager.doJumpForward(mActivity, intent);
        });
        mAutoScrollView.setDatas(response.getPage().getRows());
        mAutoScrollView.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mAutoScrollView) mAutoScrollView.reStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mAutoScrollView) mAutoScrollView.stop();
    }
}
