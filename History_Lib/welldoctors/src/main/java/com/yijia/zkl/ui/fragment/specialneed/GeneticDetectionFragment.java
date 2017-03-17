package com.yijia.zkl.ui.fragment.specialneed;

import android.content.Intent;
import android.widget.ListView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.activity.GeneticDetectionActivity;
import com.yijia.zkl.ui.holder.GeneticDetectionHolder;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.GenetestPageRequest;
import com.yijia.zkl.ui.protocol.response.GenetestPageResponse;
import com.yijia.zkl.ui.protocol.response.PageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/26.
 * 基因检测
 */
public class GeneticDetectionFragment extends SpecialBaseFragment {
    private String SAVE_KEY = "GeneticDetectionFragment";

    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.title_genetic_detection);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<GenetestPageResponse.PageBean.RowsBean>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new GeneticDetectionHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, GeneticDetectionActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
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
        if (mPageNum == AppConstants.FIRST_NUM) {
            GenetestPageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_KEY), GenetestPageResponse.class);
            if (null != response) fillView(response, false);
        }
        GenetestPageRequest request = new GenetestPageRequest();
        request.setPage(String.valueOf(mPageNum));
        Observable<GenetestPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .genetestPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<GenetestPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(GenetestPageResponse response) {
                        if (mPageNum == AppConstants.FIRST_NUM) saveInfo(SAVE_KEY, UtilsGson.toJson(response));
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

    private void fillView(GenetestPageResponse response, boolean isNet) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        if (isNet) mPageNum++;
    }

    private void fillView(GenetestPageResponse response) {
        fillView(response, true);
    }


    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
    }
}
