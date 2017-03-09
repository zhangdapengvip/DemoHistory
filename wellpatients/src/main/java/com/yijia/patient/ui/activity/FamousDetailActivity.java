package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.holder.ExpertOnlineHolder;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.QandaPageRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.OnlineqaPageResponse;
import com.yijia.patient.ui.protocol.response.QandaPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.manager.PropertyManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.WebViewVideoBaseActivity;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.picasso.UtilsPicasso;
import com.zero.library.base.view.TitleBarView;
import com.zero.library.base.view.pullrefreshview.PullToRefreshBase;
import com.zero.library.base.view.pullrefreshview.PullToRefreshListView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 名医堂——名医堂
 */
public class FamousDetailActivity extends WebViewVideoBaseActivity {
    private int mPageNum = AppConstants.FIRST_NUM;

    private PullToRefreshListView mRefreshList;
    private DefaultAdapter<QandaPageResponse.Page.Rows> mAdapter;
    private OnlineqaPageResponse.Page.Rows mInfo;
    private LoginResponse mLoginInfo;
    private ImageView mHeadPic;
    private TextView mName;
    private TextView mTitle;
    private TextView mIntroduce;

    @Override
    public int getResLayout() {
        return R.layout.activity_famous_detail;
    }

    @Override
    protected void initChildView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_famous_list);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        View headView = UtilsUi.inflate(mActivity, R.layout.head_expert_online);
        mHeadPic = (ImageView) headView.findViewById(R.id.iv_head);
        mName = (TextView) headView.findViewById(R.id.tv_name);
        mTitle = (TextView) headView.findViewById(R.id.tv_title);
        mIntroduce = (TextView) headView.findViewById(R.id.tv_introduce);
        mRefreshList = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        initListView(headView);
    }

    @Override
    public void initData() {
        mLoginInfo = UserManager.getLoginInfo();
        Intent intent = getIntent();
        mInfo = (OnlineqaPageResponse.Page.Rows) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        UtilsPicasso.loadCircleImage(mActivity, mInfo.getImage(), R.drawable.ic_default_head,
                mHeadPic, R.dimen.dimen_80);
        mName.setText(mInfo.getName());
        mTitle.setText(Constants.getTitleMap().get(mInfo.getZhicheng()));
        mIntroduce.setText(mInfo.getComment());
        requestListData();
        loadUrl(PropertyManager.getRequestHost() + "video?url=" + mInfo.getVideourl());
    }

    private void initListView(View headView) {
        ListView mListView = mRefreshList.getRefreshableView();
        mAdapter = new DefaultAdapter<QandaPageResponse.Page.Rows>(mActivity, mListView, new ArrayList<>(), AppConstants
                .PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new ExpertOnlineHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                requestListData();
            }
        };
        if (mListView.getHeaderViewsCount() == 0) mListView.addHeaderView(headView);
        mListView.setAdapter(mAdapter);
        mRefreshList.setPullRefreshEnabled(true);
        mRefreshList.setPullLoadEnabled(false);
        mRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNum = AppConstants.FIRST_NUM;
                requestListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    private void requestListData() {
        QandaPageRequest request = new QandaPageRequest();
        request.setPkOnlineqa(mInfo.getPkOnlineqa());
        request.setPage(String.valueOf(mPageNum));
        Observable<QandaPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).qandaPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<QandaPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(QandaPageResponse response) {
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

    private void fillList(QandaPageResponse response) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        mPageNum++;
    }

    protected void pullDownComplete() {
        if (null != mRefreshList) {
            mRefreshList.setLastUpdatedLabel(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_DETAIL));
            mRefreshList.onPullDownRefreshComplete();
        }
    }
}
