package com.yijia.patient.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.yijia.patient.R;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.InfodiscussSaveRequest;
import com.yijia.patient.ui.protocol.request.InformationUpdateRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.NewsPage;
import com.yijia.patient.ui.share.ShareHelper;
import com.yijia.patient.ui.share.ShareInfo;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.WebViewBaseActivity;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/25.
 * 新闻详情
 */
public class NewsDetailActivity extends WebViewBaseActivity {
    private UMShareAPI mShareAPI = null;
    private NewsPage mNewsInfo;
    private EditText mComment;
    private TextView mCommit;
    private TextView mTvUpper;
    private ImageView mIvLook;
    private LoginResponse mLoginInfo;
    private TitleBarView mTitleBar;

    @Override
    public int getResLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initChildView() {
        mShareAPI = UMShareAPI.get(mContext);
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightIcon(R.drawable.ic_right_share);
        mTitleBar.setRightListener(v -> startShare());
        mComment = (EditText) findViewById(R.id.et_comment);
        mCommit = (TextView) findViewById(R.id.tv_commit);
        mTvUpper = (TextView) findViewById(R.id.et_upper);
        mIvLook = (ImageView) findViewById(R.id.iv_look_history);
        mCommit.setOnClickListener(v -> commitComment());
        mIvLook.setOnClickListener(v -> commentHistory());
        mComment.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                UtilsUi.hideSoftInput(mActivity, mComment);
                mTvUpper.setVisibility(View.VISIBLE);
                mIvLook.setVisibility(View.VISIBLE);
                mCommit.setVisibility(View.GONE);
                mComment.setVisibility(View.GONE);
            }
        });
        mTvUpper.setOnClickListener(v -> {
            mTvUpper.setVisibility(View.GONE);
            mIvLook.setVisibility(View.GONE);
            mCommit.setVisibility(View.VISIBLE);
            mComment.setVisibility(View.VISIBLE);
            mComment.setFocusable(true);
            mComment.setFocusableInTouchMode(true);
            mComment.requestFocus();
            UtilsUi.showSoftInput(mActivity);
        });
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        mLoginInfo = UserManager.getLoginInfo();
        mNewsInfo = (NewsPage) intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        String title = intent.getStringExtra(AppConstants.EXTRA_TITLE);
        mTitleBar.setTitle(title);
        if (null == mNewsInfo) {
            JumpManager.doJumpBack(mActivity);
        }
        loadUrl(mNewsInfo.getInfourl());
        requestReadCount();
    }

    private void requestReadCount() {
        InformationUpdateRequest request = new InformationUpdateRequest();
        request.setPkInformation(mNewsInfo.getPkInformation());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).informationUpdateRequest
                (request);
        RetrofitUtils.request(mActivity, ob);
    }

    private void commentHistory() {
        Intent intent = new Intent(mActivity, CommentListActivity.class);
        intent.putExtra(AppConstants.PARCELABLE_KEY, mNewsInfo);
        JumpManager.doJumpForward(mActivity, intent);
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

    private void startShare() {
        ShareHelper helper = new ShareHelper(mActivity);
        ShareInfo info = new ShareInfo();
        info.title = UtilsUi.getString(R.string.title_share);
        info.content = mNewsInfo.getTitle();
        info.imgUrl = UtilsString.getEncodeUrl("/", mNewsInfo.getPictureurl());
        info.openUrl = UtilsString.getEncodeUrl("/", mNewsInfo.getInfourl());
        helper.share(info);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
