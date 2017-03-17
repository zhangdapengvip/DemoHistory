package com.healthsoulmate.zkl.forum.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentForumListBinding;
import com.healthsoulmate.zkl.forum.activity.ForumBlockActivity;
import com.healthsoulmate.zkl.forum.holder.ForumListHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.DiscuzsectionPageRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.healthsoulmate.zkl.forum.protocol.response.FavoriteResponse;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListFragment;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/27.
 * 论坛收藏板块列表
 */
public class ForumCollectFragment extends AppBaseListFragment {

    private FragmentForumListBinding mBinding;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_forum_list;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        super.initView(view, inflate);
        mBinding = (FragmentForumListBinding) inflate;
        mBinding.btnJumpLogin.setOnClickListener(v -> UserManager.jumpToLogin(mActivity));

        RxBus.getInstance().regist(FavoriteResponse.class).subscribe(response -> {
            mRefreshList.perfectPullRefreshSilence();
        });
    }

    @Override
    protected void onPullRefresh() {
        requestData();
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshList.perfectPullRefreshSilence();
    }

    private void requestData() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
        UtilsUi.setVisibility(mBinding.btnJumpLogin, null == loginInfo);
        if (null == loginInfo) {
            pullDownComplete();
            mAdapter.refreshData(new ArrayList<DiscuzsectionPageResponse.ListBean>());
            return;
        }
        DiscuzsectionPageRequest request = new DiscuzsectionPageRequest();
        request.setPkDiscuzsection("");
        request.setIsCollection(DiscuzsectionPageRequest.IS_COLLECTION);
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<DiscuzsectionPageResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .discuzsectionPageRequest(request);
        RetrofitUtils.request(mActivity, ob, new RetrofitUtils.ResponseListener<DiscuzsectionPageResponse>() {
            @Override
            public void beforRequest() {

            }

            @Override
            public void onSuccess(DiscuzsectionPageResponse response) {
                fillData(response);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onFinish(boolean isSuccess) {
                pullDownComplete();
            }
        });
    }

    private void fillData(DiscuzsectionPageResponse response) {
        List<DiscuzsectionPageResponse.ListBean> list = response.getList();
        UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<DiscuzsectionPageResponse.ListBean>(mActivity, listView) {
            @Override
            protected AppBaseHolder getHolder() {
                return new ForumListHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, ForumBlockActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }
}