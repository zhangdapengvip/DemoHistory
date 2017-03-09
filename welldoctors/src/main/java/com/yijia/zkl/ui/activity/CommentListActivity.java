package com.yijia.zkl.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.holder.CommentHolder;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.InfodiscussPageRequest;
import com.yijia.zkl.ui.protocol.request.InfodiscussSaveRequest;
import com.yijia.zkl.ui.protocol.response.InfoiscussPageResponse;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.yijia.zkl.ui.protocol.response.NewsPage;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseActivity;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/25.
 * 资讯评论列表
 */
public class CommentListActivity extends ListBaseActivity {
    private int mPageNum = AppConstants.FIRST_NUM;
    private NewsPage mNewsInfo;
    private EditText mComment;
    private TextView mCommit;
    private TextView mTvUpper;
    private LoginResponse mLoginInfo;
    private TextView mTvTitle;

    @Override
    public int getResLayout() {
        return R.layout.activity_comment_history;
    }

    @Override
    public void initView() {
        super.initView();
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_comment);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mComment = (EditText) findViewById(R.id.et_comment);
        mCommit = (TextView) findViewById(R.id.tv_commit);
        mTvUpper = (TextView) findViewById(R.id.et_upper);
        mCommit.setOnClickListener(v -> commitComment());
        findViewById(R.id.iv_look_history).setVisibility(View.GONE);
        mCommit.setVisibility(View.VISIBLE);
        mComment.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                UtilsUi.hideSoftInput(mActivity, mComment);
                mTvUpper.setVisibility(View.VISIBLE);
                mComment.setVisibility(View.GONE);
            }
        });
        mTvUpper.setOnClickListener(v -> {
            mTvUpper.setVisibility(View.GONE);
            mComment.setVisibility(View.VISIBLE);
            mComment.setFocusable(true);
            mComment.setFocusableInTouchMode(true);
            mComment.requestFocus();
            UtilsUi.showSoftInput(mActivity);
        });
    }

    @Override
    protected void addHeaderView(ListView listView) {
        View headView = UtilsUi.inflate(mActivity, R.layout.head_comment_list);
        mTvTitle = (TextView) headView.findViewById(R.id.tv_news_title);
        listView.addHeaderView(headView);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mNewsInfo = (NewsPage) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mNewsInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        mLoginInfo = UserManager.getLoginInfo();
        mTvTitle.setText(mNewsInfo.getTitle());
        mRefreshList.perfectPullRefresh();

    }

    private void commitComment() {
        String comment = mComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            AppToast.show(mActivity, R.string.toast_comment_empty);
            return;
        }
        InfodiscussSaveRequest request = new InfodiscussSaveRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setPkInfomation(mNewsInfo.getPkInformation());
        request.setContent(comment);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).informationSaveRequest(request);
        RetrofitUtils.request(mActivity, ob, mCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        mComment.clearFocus();
                        mComment.setText("");
                        AppToast.show(mActivity, R.string.toast_comment_success);
                        mRefreshList.perfectPullRefreshSilence();
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
        loadList();
    }

    private void loadList() {
        InfodiscussPageRequest request = new InfodiscussPageRequest();
        request.setPkInfomation(mNewsInfo.getPkInformation());
        request.setPage(String.valueOf(mPageNum));
        Observable<InfoiscussPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                infodiscussPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<InfoiscussPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(InfoiscussPageResponse response) {
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

    private void fillList(InfoiscussPageResponse response) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        mPageNum++;
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<InfoiscussPageResponse.Page.Rows>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new CommentHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }
        };
    }
}