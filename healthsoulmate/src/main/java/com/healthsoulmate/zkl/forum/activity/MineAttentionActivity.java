package com.healthsoulmate.zkl.forum.activity;

import android.databinding.ViewDataBinding;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityMineAttenttionBinding;
import com.healthsoulmate.zkl.forum.holder.MineAttentionHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.UserfocusPageRequest;
import com.healthsoulmate.zkl.forum.protocol.response.UserfocusPageResponse;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListActivity;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/2.
 * 我的关注
 */
public class MineAttentionActivity extends AppBaseListActivity {

    private int mPageNum = AppConstants.FIRST_NUM;
    private ActivityMineAttenttionBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_mine_attenttion;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityMineAttenttionBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_mine_attention);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<UserfocusPageResponse.PageBean.RowsBean>(mActivity, listView,
                AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new MineAttentionHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                UserManager.jumpToOtherPeople(mActivity, getData().get(position).getPkUserbefocus());
            }

            @Override
            public void onLoadMore() {
                requestList();
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestList();
    }

    public void requestList() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        UserfocusPageRequest request = new UserfocusPageRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPage(String.valueOf(mPageNum));
        Observable<UserfocusPageResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .userfocusPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<UserfocusPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(UserfocusPageResponse response) {
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

    private void fillList(UserfocusPageResponse response) {
        List<UserfocusPageResponse.PageBean.RowsBean> list = response.getPage().getRows();
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(list);
            UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        } else {
            mAdapter.loadData(list);
        }
        mPageNum++;
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
    }
}
