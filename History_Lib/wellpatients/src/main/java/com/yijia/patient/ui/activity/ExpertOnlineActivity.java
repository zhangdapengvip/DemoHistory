package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.holder.ExpertOnlineHolder;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.QandaPageRequest;
import com.yijia.patient.ui.protocol.request.QandaSaveRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.OnlineqaPageResponse;
import com.yijia.patient.ui.protocol.response.QandaPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.picasso.UtilsPicasso;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.TitleBarView;
import com.zero.library.base.view.pullrefreshview.PullToRefreshBase;
import com.zero.library.base.view.pullrefreshview.PullToRefreshListView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 名医堂——专家在线问题列表
 */
public class ExpertOnlineActivity extends DefaultActivity {
    private int mPageNum = AppConstants.FIRST_NUM;

    private PullToRefreshListView mRefreshList;
    private DefaultAdapter<QandaPageResponse.Page.Rows> mAdapter;
    private OnlineqaPageResponse.Page.Rows mInfo;
    private LoginResponse mLoginInfo;
    private ImageView mHeadPic;
    private TextView mName;
    private TextView mTitle;
    private TextView mIntroduce;
    private EditText mComment;
    private TextView mCommit;
    private TextView mTvUpper;
    private ImageView mIvLook;
    private AppAlertDialog mNoticeDialog;

    @Override
    public int getResLayout() {
        return R.layout.activity_expert_online;
    }

    @Override
    public void initView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.label_expert_online_name);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        View headView = UtilsUi.inflate(mActivity, R.layout.head_expert_online);
        mHeadPic = (ImageView) headView.findViewById(R.id.iv_head);
        mName = (TextView) headView.findViewById(R.id.tv_name);
        mTitle = (TextView) headView.findViewById(R.id.tv_title);
        mIntroduce = (TextView) headView.findViewById(R.id.tv_introduce);
        mRefreshList = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        initListView(headView);

        mComment = (EditText) findViewById(R.id.et_comment);
        mCommit = (TextView) findViewById(R.id.tv_commit);
        mTvUpper = (TextView) findViewById(R.id.et_upper);
        mIvLook = (ImageView) findViewById(R.id.iv_look_history);
        mCommit.setOnClickListener(v -> commitQuestion());
        mIvLook.setVisibility(View.GONE);
        mCommit.setVisibility(View.VISIBLE);
        mCommit.setText(R.string.btn_send_message);
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
    public void initData() {
        mLoginInfo = UserManager.getLoginInfo();
        Intent intent = getIntent();
        mInfo = (OnlineqaPageResponse.Page.Rows) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        UtilsPicasso.loadCircleImage(mActivity, mInfo.getImage(), R.drawable.ic_default_head,
                mHeadPic, R.dimen.dimen_80);
        mName.setText(mInfo.getName());
        mTitle.setText(Constants.getTitleMap().get(mInfo.getZhicheng()));
        mIntroduce.setText(mInfo.getComment());
        requestListData();
    }

    private void initListView(View headView) {
        ListView mListView = mRefreshList.getRefreshableView();
        mAdapter = new DefaultAdapter<QandaPageResponse.Page.Rows>(mActivity, mListView, new ArrayList<>(), AppConstants
                .PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new ExpertOnlineHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                requestListData();
            }
        };
        if (mListView.getHeaderViewsCount() == 0) mListView.addHeaderView(headView);
        mListView.setAdapter(mAdapter);
        mRefreshList.setPullRefreshEnabled(true);
        mRefreshList.setPullLoadEnabled(false);
        mRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNum = AppConstants.FIRST_NUM;
                requestListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    private void commitQuestion() {
        String question = mComment.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, question, R.string.toast_question_empty)) {
            return;
        }
        QandaSaveRequest request = new QandaSaveRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setPkOnlineqa(mInfo.getPkOnlineqa());
        request.setQuestion(question);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).qandaSaveRequest(request);
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
                        mNoticeDialog = UtilsUi.getNoticeDialog(mActivity, UtilsUi.getString(R.string
                                .dialog_send_success));
                        mNoticeDialog.show();
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

    private void requestListData() {
        QandaPageRequest request = new QandaPageRequest();
        request.setPkOnlineqa(mInfo.getPkOnlineqa());
        request.setPage(String.valueOf(mPageNum));
        Observable<QandaPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).qandaPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<QandaPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(QandaPageResponse response) {
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

    private void fillList(QandaPageResponse response) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        mPageNum++;
    }

    protected void pullDownComplete() {
        if (null != mRefreshList) {
            mRefreshList.setLastUpdatedLabel(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_DETAIL));
            mRefreshList.onPullDownRefreshComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mNoticeDialog) mNoticeDialog.dismiss();
    }
}
