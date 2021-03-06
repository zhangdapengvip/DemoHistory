package com.yijia.patient.ui.fragment.homechild;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.MineActiviity;
import com.yijia.patient.ui.activity.ViewDetailActivity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.bean.OfficeInfo;
import com.yijia.patient.ui.holder.ViewNewsHolder;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.ViewPageRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.ViewPageResponse;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseFragment;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ZeroAries on 2016/6/24.
 * 科普讲座
 */
public class LectureFragment extends ListBaseFragment {
    private String SAVE_KEY = "LectureFragment";
    private int mPageNum = AppConstants.FIRST_NUM;
    private Subscription mRxBus;
    private Subscriber<? super ViewPageResponse> mSubscriber;
    private TitleBarView mTitleBar;

    @Override
    public String getPagerTitle() {
        return "";
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_lecture;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        LoginResponse loginInfo = UserManager.getLoginInfo();
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.tab_two);
        mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadList();
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<ViewPageResponse.Page.Rows>(mActivity,
                listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new ViewNewsHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, ViewDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                intent.putExtra(AppConstants.EXTRA_TITLE, getData().get(position).getTitle());
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
        mRxBus = RxBus.getInstance().regist(OfficeInfo.class).subscribe(s -> {
            if (OfficeInfo.TYPE_VIEW.equals(s.getViewType())) {
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

    private void loadList() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            ViewPageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_KEY), ViewPageResponse.class);
            if (null != response) fillView(response, false);
        }
        ViewPageRequest request = new ViewPageRequest();
        request.setPage(String.valueOf(mPageNum));
        Observable<ViewPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).viewPageRequest(request);
        mSubscriber = RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<ViewPageResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(ViewPageResponse response) {
                        if (mPageNum == AppConstants.FIRST_NUM)
                            saveInfo(SAVE_KEY, UtilsGson.toJson(response));
                        fillView(response);
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

    private void fillView(ViewPageResponse response, boolean isNet) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        if (isNet) mPageNum++;
    }

    private void fillView(ViewPageResponse response) {
        fillView(response, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mTitleBar) {
            LoginResponse loginInfo = UserManager.getLoginInfo();
            mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mRxBus) mRxBus.unsubscribe();
    }
}