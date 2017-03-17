package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityPostHistoryBinding;
import com.healthsoulmate.zkl.forum.holder.ForumHistoryHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.PostFavoritesFavoriteRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostFavoritesMyFavoritesRequest;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListActivity;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.AppEmptyDialog;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/26.
 * 帖子收藏列表
 */
public class ForumHistoryActivity extends AppBaseListActivity {
    private int mPageNum = AppConstants.FIRST_NUM;
    private AppAlertDialog mUnSaveDialog;
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
        mBinding.titleBar.setTitle(R.string.title_mine_store);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<PostsPageResponse.PageBean.RowsBean>(mActivity, listView, AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new ForumHistoryHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, ForumDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
            }

            @Override
            public void onLoadMore() {
                requestList();
            }

            @Override
            public boolean onItemLongClickInner(AdapterView<?> parent, View view, int position, long id) {
                mUnSaveDialog = UtilsUi.getNoticeDialog(mActivity, "提示", "取消收藏？", "立即取消", "暂不取消",
                        (dialog, btnView) -> {
                            if (btnView == AppEmptyDialog.BtnView.LEFT) {
                                unSaveRequest(getData().get(position));
                            }
                            dialog.dismiss();
                        });
                mUnSaveDialog.show();
                return true;
            }
        };
    }

    private void unSaveRequest(PostsPageResponse.PageBean.RowsBean rowsBean) {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        PostFavoritesFavoriteRequest request = new PostFavoritesFavoriteRequest();
        request.setPkPosts(rowsBean.getPkPosts());
        request.setPkPostfavorites(rowsBean.getPkPostfavorites());
        request.setFlag(PostFavoritesFavoriteRequest.DELETE);
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .postFavoritesFavoriteRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, "取消成功");
                        mRefreshList.perfectPullRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestList();
    }

    private void requestList() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) {
            pullDownComplete();
            return;
        }
        PostFavoritesMyFavoritesRequest request = new PostFavoritesMyFavoritesRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPage(String.valueOf(mPageNum));
        Observable<PostsPageResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).postFavoritesMyFavoritesRequest
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
                        mAdapter.loadError();
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillList(PostsPageResponse response) {
        List<PostsPageResponse.PageBean.RowsBean> list = response.getPage().getRows();
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(list);
            UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        } else {
            mAdapter.loadData(list);
        }
        mPageNum++;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnSaveDialog) mUnSaveDialog.dismiss();
    }
}
