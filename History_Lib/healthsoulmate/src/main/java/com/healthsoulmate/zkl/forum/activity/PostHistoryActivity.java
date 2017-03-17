package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityPostHistoryBinding;
import com.healthsoulmate.zkl.forum.holder.PostHistoryHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.MyPostHistoryRequest;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
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
 * Created by ZeroAries on 2016/5/26.
 * 我发布的帖子
 */
public class PostHistoryActivity extends AppBaseListActivity {
    private String mPkUser;
    private ActivityPostHistoryBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_post_history;
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityPostHistoryBinding) viewDataBinding;
        mPkUser = getIntent().getStringExtra(AppConstants.EXTRA_STRING);
        if (TextUtils.isEmpty(mPkUser)) JumpManager.doJumpBack(mActivity);
        mBinding.titleBar.setTitle(R.string.title_mine_forum);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<PostsPageResponse.PageBean.RowsBean>(mActivity, listView) {
            @Override
            protected AppBaseHolder getHolder() {
                return new PostHistoryHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, ForumDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        requestList();
    }

    private void requestList() {
        MyPostHistoryRequest request = new MyPostHistoryRequest();
        request.setPkUser(mPkUser);
        Observable<PostsPageResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).myPostHistoryRequest
                (request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PostsPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(PostsPageResponse response) {
                        fillList(response);
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

    private void fillList(PostsPageResponse response) {
        List<PostsPageResponse.PageBean.RowsBean> list = response.getPage().getRows();
        UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }
}
