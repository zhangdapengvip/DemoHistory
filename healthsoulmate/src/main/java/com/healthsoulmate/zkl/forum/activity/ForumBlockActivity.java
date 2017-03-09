package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityTitleListBinding;
import com.healthsoulmate.zkl.databinding.HeaderForumBlockBinding;
import com.healthsoulmate.zkl.forum.holder.ForumBlockHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.PostsPageRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListActivity;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/28.
 * 论坛板块内容列表
 */
public class ForumBlockActivity extends AppBaseListActivity {

    private int mPageNum = AppConstants.FIRST_NUM;
    private DiscuzsectionPageResponse.ListBean mBlockInfo;
    private ActivityTitleListBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_title_list;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityTitleListBinding) viewDataBinding;
        mBinding.titleBar.setTitle(mBlockInfo.getSectionname());
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText("发帖");
        mBinding.titleBar.setRightListener(v -> {
            Intent intent = new Intent(mActivity, ReleasePostActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, mBlockInfo);
            JumpManager.doJumpForwardWithResult(mActivity, intent, 1001);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRefreshList.perfectPullRefresh();
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestData();
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
    }


    private void requestData() {
        PostsPageRequest request = new PostsPageRequest();
        request.setPkDiscuzsection(mBlockInfo.getPkDiscuzsection());
        request.setPage(String.valueOf(mPageNum));
        Observable<PostsPageResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).postsPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PostsPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(PostsPageResponse response) {
                        fillData(response);
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

    private void fillData(PostsPageResponse response) {
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
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<PostsPageResponse.PageBean.RowsBean>(mActivity, listView, AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new ForumBlockHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, ForumDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                intent.putExtra(AppConstants.PARCELABLE_KEY_TWO, mBlockInfo);
                JumpManager.doJumpForward(mActivity, intent);
            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        };
    }

    @Override
    protected void addHeaderView(ListView listView) {
        Intent intent = getIntent();
        mBlockInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mBlockInfo) JumpManager.doJumpBack(mActivity);

        HeaderForumBlockBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.header_forum_block, null, false);
        View viewContent = inflate.getRoot();
        viewContent.setOnClickListener(v -> {
            Intent jumpIntent = new Intent(mActivity, BlockIntroduceActivity.class);
            jumpIntent.putExtra(AppConstants.PARCELABLE_KEY, mBlockInfo);
            JumpManager.doJumpForward(mActivity, jumpIntent);
        });
        inflate.tvIntroduce.setText(mBlockInfo.getAbout());
        inflate.setImgInfo(new ImgBinding(mBlockInfo.getImageurl(), R.drawable.ic_default_head, R.dimen.dimen_55));
        listView.addHeaderView(viewContent);
    }
}
