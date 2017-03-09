package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityForumDetailBinding;
import com.healthsoulmate.zkl.databinding.HeaderForumDetailBinding;
import com.healthsoulmate.zkl.databinding.ItemForumImageBinding;
import com.healthsoulmate.zkl.databinding.PopuFloorHostBinding;
import com.healthsoulmate.zkl.forum.bean.ImgInfo;
import com.healthsoulmate.zkl.forum.bean.ReplyInfo;
import com.healthsoulmate.zkl.forum.factory.ReadHistoryManager;
import com.healthsoulmate.zkl.forum.holder.ForumDetailHolder;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.DiscussreplySaveRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostFavoritesFavoriteRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostbrowseAddReadsRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostsDetailPostsRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
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
import com.zero.library.base.view.AppPopupWindow;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by ZeroAries on 2016/4/29.
 * 帖子内容详细
 */
public class ForumDetailActivity extends AppBaseListActivity {

    private int mPageNum = AppConstants.FIRST_NUM;
    private ActivityForumDetailBinding mBinding;
    private Subscription mSubscript;
    private int maxHeight;
    private PostsPageResponse.PageBean.RowsBean mForumInfo;
    private DiscuzsectionPageResponse.ListBean mBlockInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_forum_detail;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityForumDetailBinding) viewDataBinding;
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText(R.string.title_right_store);
        mBinding.titleBar.setRightListener(V -> doStore());

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
        requestAddReadCount();
    }

    @Override
    protected void addHeaderView(ListView listView) {
        Intent intent = getIntent();
        mForumInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY);
        mBlockInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY_TWO);
        if (null == mForumInfo) JumpManager.doJumpBack(mActivity);
        if (TextUtils.isEmpty(mForumInfo.getSectionname()))
            mForumInfo.setSectionname(null == mBlockInfo ?
                    getString(R.string.string_history) : mBlockInfo.getSectionname());
        ReadHistoryManager.add(mForumInfo);
        HeaderForumDetailBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.header_forum_detail, null, false);
        inflate.setImgInfo(new ImgBinding(mForumInfo.getUserImage(), R.drawable.ic_default_head, R.dimen.dimen_30, 10));
        inflate.tvName.setOnClickListener(v -> UserManager.jumpToOtherPeople(mActivity, mForumInfo.getPkUser()));
        inflate.setInfo(mForumInfo);
        String images = mForumInfo.getImages();
        if (!TextUtils.isEmpty(images)) {
            String[] split = images.split(",");
            inflate.llImgContent.removeAllViews();
            ArrayList<ImgInfo> imgList = new ArrayList<>();
            for (String url : split) {
                ImgInfo info = new ImgInfo();
                info.setPath(url);
                imgList.add(info);
            }
            for (int position = 0; position < imgList.size(); position++) {
                ItemForumImageBinding imgBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                        R.layout.item_forum_image, null, false);
                imgBinding.setImgInfo(new ImgBinding(imgList.get(position).getPath(), R.drawable.ic_default_img));
                final int currentPosition = position;
                imgBinding.ivInfo.setOnClickListener(v -> {
                    Intent photoIntent = new Intent(mActivity, PhotoPreviewActivity.class);
                    photoIntent.putParcelableArrayListExtra(AppConstants.PARCELABLE_KEY, imgList);
                    photoIntent.putExtra(PhotoPreviewActivity.POSITION, currentPosition);
                    photoIntent.putExtra(PhotoPreviewActivity.HIDENDELETE, true);
                    JumpManager.doJumpForward(mActivity, photoIntent);
                });
                inflate.llImgContent.addView(imgBinding.getRoot());
            }
        }
        inflate.tvForumName.setText(mBlockInfo == null ? mForumInfo.getSectionname() : mBlockInfo.getSectionname());
        inflate.tvForumName.setOnClickListener(v -> JumpManager.doJumpBack(mActivity));
        inflate.ivShowAction.setOnClickListener(v -> showAction(inflate));
        listView.addHeaderView(inflate.getRoot());
    }

    private void doStore() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        PostFavoritesFavoriteRequest request = new PostFavoritesFavoriteRequest();
        request.setPkPosts(mForumInfo.getPkPosts());
        request.setFlag(PostFavoritesFavoriteRequest.ADD);
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .postFavoritesFavoriteRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.titleBar.getRightView(),
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, R.string.toast_store_success);
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
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<PostsDetailPostsResponse.PageBean.RowsBean>(mActivity, listView) {
            @Override
            protected AppBaseHolder getHolder() {
                return new ForumDetailHolder(mActivity, mForumInfo);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mRefreshList.perfectPullRefreshSilence();
        }
    }

    private void requestData() {
        PostsDetailPostsRequest request = new PostsDetailPostsRequest();
        request.setPkPosts(mForumInfo.getPkPosts());
        request.setPage(String.valueOf(mPageNum));
        Observable<PostsDetailPostsResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .postsDetailPostsRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PostsDetailPostsResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(PostsDetailPostsResponse response) {
                        fillData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPageNum == AppConstants.FIRST_NUM) fillEmptyData();
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillEmptyData() {
        List<PostsDetailPostsResponse.PageBean.RowsBean> dataList = new ArrayList<>();
        dataList.add(new PostsDetailPostsResponse.PageBean.RowsBean());
        mAdapter.refreshData(dataList);
    }

    private void fillData(PostsDetailPostsResponse response) {
        List<PostsDetailPostsResponse.PageBean.RowsBean> dataList = response.getPage().getRows();
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
            if (replyInfo.getTag().equals(ForumDetailActivity.class.getSimpleName())) {
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

    /**
     * 显示选择列表
     */
    private void showAction(HeaderForumDetailBinding floor) {
        ((ForumDetailActivity) mActivity).hidenReply();
        PopuFloorHostBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.popu_floor_host, null, false);
        View rootView = inflate.getRoot();
        ImageView parentView = floor.ivShowAction;
        AppPopupWindow popupWindow = new AppPopupWindow(mActivity, parentView);
        inflate.btnReply.setVisibility(View.GONE);
        inflate.btnComment.setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(mActivity, ReplyCommentActivity.class);
            intent.putExtra(AppConstants.EXTRA_STRING, mForumInfo.getPkPosts());
            JumpManager.doJumpForwardWithResult(mActivity, intent, 1002);
        });
        inflate.btnReport.setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(mActivity, ReprotActivity.class);
            intent.putExtra(ReprotActivity.PK_POSTS, mForumInfo.getPkPosts());
            intent.putExtra(ReprotActivity.PK_USER, mForumInfo.getPkUser());
            intent.putExtra(ReprotActivity.CONTENT, mForumInfo.getContent());
            JumpManager.doJumpForward(mActivity, intent);
        });

        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(rootView);
        int[] location = new int[2];
        parentView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        rootView.measure(0, 0);
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY,
                x + parentView.getWidth() - rootView.getMeasuredWidth(),
                y + parentView.getHeight() / 2 - rootView.getMeasuredHeight() / 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSubscript) mSubscript.unsubscribe();
    }


    private void requestAddReadCount() {
        PostbrowseAddReadsRequest request = new PostbrowseAddReadsRequest();
        request.setPkPosts(mForumInfo.getPkPosts());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).postbrowseAddReadsRequest(request);
        RetrofitUtils.request(mActivity, ob);
    }
}