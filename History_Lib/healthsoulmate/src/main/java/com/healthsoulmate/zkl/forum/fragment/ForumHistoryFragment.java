package com.healthsoulmate.zkl.forum.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentForumListBinding;
import com.healthsoulmate.zkl.forum.activity.ForumDetailActivity;
import com.healthsoulmate.zkl.forum.factory.ReadHistoryManager;
import com.healthsoulmate.zkl.forum.holder.ForumBlockHolder;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListFragment;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;

import java.util.List;

/**
 * Created by ZeroAries on 2016/5/5.
 * 最近访问
 */
public class ForumHistoryFragment extends AppBaseListFragment {

    private FragmentForumListBinding mBinding;

    @Override
    public String getPagerTitle() {
        return "最近访问";
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_forum_list;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        super.initView(view, inflate);
        mBinding = (FragmentForumListBinding) inflate;
        mBinding.tvEmpty.setText(R.string.notice_history_empty);
    }

    @Override
    protected void onPullRefresh() {
        refreshData();
        pullDownComplete();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        List<PostsPageResponse.PageBean.RowsBean> list = ReadHistoryManager.getLoacl();
        UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
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
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

}
