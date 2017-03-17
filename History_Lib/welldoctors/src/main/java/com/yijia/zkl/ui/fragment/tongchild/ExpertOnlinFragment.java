package com.yijia.zkl.ui.fragment.tongchild;

import android.content.Intent;
import android.widget.ListView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.activity.ExpertOnlineActivity;
import com.yijia.zkl.ui.holder.OnlineQaHolder;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.OnlineqaPageRequest;
import com.yijia.zkl.ui.protocol.response.OnlineqaPageResponse;
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
 * Created by ZeroAries on 2016/3/11.
 * 专家在线
 */
public class ExpertOnlinFragment extends TongBaseFragment {
    private String SAVE_KEY = "ExpertOnlinFragment";

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadList();
    }

    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_expert_online_name);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<OnlineqaPageResponse.Page.Rows>(mActivity,
                listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new OnlineQaHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                OnlineqaPageResponse.Page.Rows info = getData().get(position);
                Intent intent = new Intent(mActivity, ExpertOnlineActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, info);
                JumpManager.doJumpForward(mActivity, intent);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }
        };
    }

    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
    }

    private void loadList() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            OnlineqaPageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_KEY), OnlineqaPageResponse.class);
            if (null != response) fillView(response, false);
        }
        OnlineqaPageRequest request = new OnlineqaPageRequest();
        request.setPage(String.valueOf(mPageNum));
        request.setType(OnlineqaPageRequest.TYPE_ONLINE);
        Observable<OnlineqaPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).onlineqaPageRequest
                (request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<OnlineqaPageResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(OnlineqaPageResponse response) {
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

    private void fillView(OnlineqaPageResponse response, boolean isNet) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        if (isNet) mPageNum++;
    }

    private void fillView(OnlineqaPageResponse response) {
        fillView(response, true);
    }
}
