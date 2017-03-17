package com.yijia.patient.ui.fragment.newschild;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.NewsDetailActivity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.bean.OfficeInfo;
import com.yijia.patient.ui.holder.NewsHolder;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.InformationHeadRequest;
import com.yijia.patient.ui.protocol.request.InformationPageRequest;
import com.yijia.patient.ui.protocol.response.InformationHeadResponse;
import com.yijia.patient.ui.protocol.response.InformationPageResponse;
import com.yijia.patient.ui.protocol.response.NewsPage;
import com.yijia.patient.ui.view.ScrollingViewPager;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseFragment;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ZeroAries on 2016/3/10.
 * 资讯Tab基类
 */
public abstract class NewsBaseFragment extends ListBaseFragment {
    private String SAVE_KEY = "NewsBaseFragment" + getType();
    private String SAVE_HEADKEY = NewsBaseFragment.class.getSimpleName() + "HEAD" + getType();

    private int mPageNum = AppConstants.FIRST_NUM;
    private ScrollingViewPager mAutoScrollView;
    private Subscription mRxBus;
    private Subscriber<? super InformationPageResponse> mSubscriber;

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
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadAdvert();
        loadList();
    }

    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
        mRxBus = RxBus.getInstance().regist(OfficeInfo.class).subscribe(s -> {
            if (OfficeInfo.TYPE_NEWS.equals(s.getViewType())) {
                if (isShowing()) {
                    if (null != mSubscriber) mSubscriber.unsubscribe();
                    mRefreshList.perfectPullRefresh();
                } else {
                    mRefreshList.cleanRefreshTime();
                }
            }
        });
    }

    @Override
    public void onSelected() {
        super.onSelected();
        if (null != mRefreshList) mRefreshList.perfectPullRefresh(Constants.REFRESH_TIME);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<NewsPage>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new NewsHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                NewsPage itemInfo = getData().get(position);
                intent.putExtra(AppConstants.PARCELABLE_KEY, itemInfo);
                intent.putExtra(AppConstants.EXTRA_TITLE, getPagerTitle());
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

    private void loadList() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            InformationPageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_KEY),
                    InformationPageResponse.class);
            if (null != response) fillList(response, false);
        }
        InformationPageRequest request = new InformationPageRequest();
        request.setPage(String.valueOf(mPageNum));
        request.setType(getType());
        Observable<InformationPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .informationPageRequest(request);
        mSubscriber = RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<InformationPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(InformationPageResponse response) {
                        if (mPageNum == AppConstants.FIRST_NUM)
                            saveInfo(SAVE_KEY, UtilsGson.toJson(response));
                        fillList(response);
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

    private void fillList(InformationPageResponse request, boolean isNet) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(request.getPage().getRows());
        } else {
            mAdapter.loadData(request.getPage().getRows());
        }
        if (isNet) mPageNum++;
    }

    private void fillList(InformationPageResponse request) {
        fillList(request, true);
    }

    private void loadAdvert() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            InformationHeadResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_HEADKEY),
                    InformationHeadResponse.class);
            if (null != response) fillAdvert(response);
        }
        InformationHeadRequest request = new InformationHeadRequest();
        request.setType(getType());
        Observable<InformationHeadResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .informationHeadRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<InformationHeadResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(InformationHeadResponse response) {
                        if (mPageNum == AppConstants.FIRST_NUM)
                            saveInfo(SAVE_HEADKEY, UtilsGson.toJson(response));
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

    private void fillAdvert(InformationHeadResponse response) {
        mAutoScrollView.setOnPagerClickListener(position -> {
            NewsPage item = mAutoScrollView.getDatas().get(position);
            if (!TextUtils.isEmpty(item.getInfourl())) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, item);
                intent.putExtra(AppConstants.EXTRA_TITLE, getPagerTitle());
                JumpManager.doJumpForward(mActivity, intent);
            }
        });
        mAutoScrollView.setDatas(response.getList());
        mAutoScrollView.start();
    }

    public abstract String getType();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mRxBus) mRxBus.unsubscribe();
    }
}