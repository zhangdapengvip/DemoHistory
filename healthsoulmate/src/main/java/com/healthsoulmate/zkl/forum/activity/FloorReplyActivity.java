package com.healthsoulmate.zkl.forum.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityFloorReplyBinding;
import com.healthsoulmate.zkl.databinding.HeaderFloorReplyBinding;
import com.healthsoulmate.zkl.databinding.ItemForumImageBinding;
import com.healthsoulmate.zkl.forum.bean.ReplyInfo;
import com.healthsoulmate.zkl.forum.holder.FloorReplyHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.ProtocolConstants;
import com.healthsoulmate.zkl.forum.protocol.request.DiscussreplySaveRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PageDiscussReplyRequest;
import com.healthsoulmate.zkl.forum.protocol.response.PageDiscussReplyResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsDetailPostsResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListActivity;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by ZeroAries on 2016/5/5.
 * 楼层回复详情
 */
public class FloorReplyActivity extends AppBaseListActivity {

    private int mPageNum = AppConstants.FIRST_NUM;
    private Subscription mSubscript;
    private ActivityFloorReplyBinding mBinding;
    private int maxHeight;
    private PostsDetailPostsResponse.PageBean.RowsBean mFloorInfo;
    private PostsPageResponse.PageBean.RowsBean mForumInfo;
    private int mFloor;

    @Override
    public int getResLayout() {
        return R.layout.activity_floor_reply;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityFloorReplyBinding) viewDataBinding;
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mRefreshList.getRefreshableView().setVerticalScrollBarEnabled(false);
        mBinding.rlContent.setChanged((w, h, oldw, oldh) -> {
            maxHeight = Math.max(maxHeight, h);
            if (maxHeight > h) {
                mRefreshList.getRefreshableView().setOnTouchListener((v, event) -> {
                    hidenReply();
                    return false;
                });
            } else {
                mRefreshList.getRefreshableView().setOnTouchListener((v, event) -> false);
            }
        });
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<PageDiscussReplyResponse.PageBean.RowsBean>(mActivity, listView,
                AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new FloorReplyHolder(mActivity, mFloorInfo);
            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestData();
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
        registListener();
    }


    private void requestData() {
        PageDiscussReplyRequest request = new PageDiscussReplyRequest();
        request.setPage(String.valueOf(mPageNum));
        request.setPkDiscuss(mFloorInfo.getPkDiscuss());
        Observable<PageDiscussReplyResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .pageDiscussReplyRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PageDiscussReplyResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(PageDiscussReplyResponse response) {
                        fillData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPageNum == AppConstants.FIRST_NUM) fillEmptyData();
                        mAdapter.loadError();
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillEmptyData() {
        List<PageDiscussReplyResponse.PageBean.RowsBean> dataList = new ArrayList<>();
        dataList.add(new PageDiscussReplyResponse.PageBean.RowsBean());
        mAdapter.refreshData(dataList);
    }

    private void fillData(PageDiscussReplyResponse response) {
        List<PageDiscussReplyResponse.PageBean.RowsBean> dataList = response.getPage().getRows();
        if (mPageNum == AppConstants.FIRST_NUM) {
            if (dataList.size() == 0) {
                fillEmptyData();
            } else {
                mAdapter.refreshData(dataList);
            }
        } else {
            mAdapter.loadData(dataList);
        }
        mPageNum++;
    }

    private void registListener() {
        mSubscript = RxBus.getInstance().regist(ReplyInfo.class).subscribe(replyInfo -> {
            if (replyInfo.getTag().equals(FloorReplyActivity.class.getSimpleName())) {
                mBinding.llReplyContent.setVisibility(View.VISIBLE);
                mBinding.etReply.requestFocus();
                mBinding.etReply.setHint("回复" + replyInfo.getReplyName());
                mBinding.btnSend.setOnClickListener(v -> replyCommit(replyInfo));
                UtilsUi.showSoftInput(mActivity);
            }
        });
    }

    /**
     * 回复楼层
     *
     * @param info 回复对象信息
     */
    private void replyCommit(ReplyInfo info) {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        String replyContent = mBinding.etReply.getText().toString().trim();
        if (TextUtils.isEmpty(replyContent)) {
            AppToast.show(mActivity, "请输入回复内容");
            return;
        }
        DiscussreplySaveRequest request = new DiscussreplySaveRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPkDiscuss(info.getPkDiscuss());
        request.setPkUserreply(info.getPkReply());
        request.setContent(replyContent);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .discussreplySaveRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        mBinding.etReply.setText("");
                        mRefreshList.perfectPullRefreshSilence();
                        hidenReply();
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


    public void hidenReply() {
        mBinding.llReplyContent.setVisibility(View.GONE);
        UtilsUi.hideSoftInput(mActivity);
    }

    @Override
    protected void addHeaderView(ListView listView) {
        mFloorInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY);
        mForumInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY_TWO);
        mFloor = getIntent().getIntExtra(AppConstants.EXTRA_STRING, 0);
        if (null == mFloorInfo || null == mForumInfo) JumpManager.doJumpBack(mActivity);
        listView.setDivider(new BitmapDrawable());
        HeaderFloorReplyBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.header_floor_reply, null, false);
        inflate.tvName.setOnClickListener(v -> UserManager.jumpToOtherPeople(mActivity, mForumInfo.getPkUser()));
        UtilsUi.setVisibility(inflate.tvIsfloorer, mForumInfo.getPkUser().equals(mFloorInfo.getPkUser()));
        inflate.setImgInfo(new ImgBinding(mFloorInfo.getUserImage(), R.drawable.ic_default_head, R.dimen.dimen_30));
        inflate.tvFloor.setText(mActivity.getString(R.string.format_floor, mFloor));
        inflate.setInfo(mFloorInfo);
        String images = mForumInfo.getImages();
        if (!TextUtils.isEmpty(images)) {
            String[] split = images.split(",");
            inflate.llImgContent.removeAllViews();
            for (String url : split) {
                ItemForumImageBinding imgBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                        R.layout.item_forum_image, null, false);
                imgBinding.setImgInfo(new ImgBinding(url, R.drawable.ic_default_img));
                inflate.llImgContent.addView(imgBinding.getRoot());
            }
        }
        listView.addHeaderView(inflate.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSubscript) mSubscript.unsubscribe();
    }
}
