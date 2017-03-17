package com.yijia.patient.ui.fragment.specialneed;

import android.content.Intent;
import android.widget.ListView;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.ScientificIntroduceActivity;
import com.yijia.patient.ui.holder.ScientificInHolder;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.PageRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.PageResponse;
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
 * 科研参与
 */
public class ScientificInFragment extends SpecialBaseFragment {
    private String SAVE_KEY = "ScientificInFragment";

    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_scientific_in_name);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<PageResponse.Page.Rows>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new ScientificInHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, ScientificIntroduceActivity.class);
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
    protected void initData() {
        mRefreshList.perfectPullRefresh();
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadList();
    }

    private void loadList() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            PageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_KEY), PageResponse.class);
            if (null != response) fillView(response, false);
        }
        LoginResponse loginInfo = UserManager.getLoginInfo();
        PageRequest request = new PageRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPage(String.valueOf(mPageNum));
        Observable<PageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).pageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(PageResponse response) {
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

    private void fillView(PageResponse response, boolean isNet) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        if (isNet) mPageNum++;
    }

    private void fillView(PageResponse response) {
        fillView(response, true);
    }
}
