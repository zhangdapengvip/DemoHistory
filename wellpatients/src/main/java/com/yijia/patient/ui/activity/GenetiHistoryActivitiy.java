package com.yijia.patient.ui.activity;

import android.widget.ListView;

import com.yijia.patient.R;
import com.yijia.patient.ui.holder.GenetiHistoryHolder;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.GeneapplyPageRequest;
import com.yijia.patient.ui.protocol.response.GeneapplyPageResponse;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseActivity;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/28.
 * 基因检测推荐历史
 */
public class GenetiHistoryActivitiy extends ListBaseActivity {

    private int mPageNum = AppConstants.FIRST_NUM;
    private LoginResponse mLoginInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_geneti_history;
    }

    @Override
    public void initView() {
        super.initView();
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        titleBar.setTitle(R.string.title_geneti_history);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<GeneapplyPageResponse.PageBean.RowsBean>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {
            @Override
            protected AppBaseHolder getHolder() {
                return new GenetiHistoryHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadList();
    }

    private void loadList() {
        GeneapplyPageRequest request = new GeneapplyPageRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setPage(String.valueOf(mPageNum));
        Observable<GeneapplyPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).geneapplyPageRequest
                (request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<GeneapplyPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(GeneapplyPageResponse response) {
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

    private void fillList(GeneapplyPageResponse response) {
        List<GeneapplyPageResponse.PageBean.RowsBean> list = response.getPage().getRows();
        UtilsUi.setVisibility(mTvEmpty, list.size() <= 0);
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(list);
        } else {
            mAdapter.loadData(list);
        }
        mPageNum++;
    }

    @Override
    public void initData() {
        mLoginInfo = UserManager.getLoginInfo();
        mRefreshList.perfectPullRefresh();
    }
}
