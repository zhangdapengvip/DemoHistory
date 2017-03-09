package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityReportBinding;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.PostreportSaveRequest;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsUi;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/9.
 * 举报
 */
public class ReprotActivity extends AppBaseActivity {

    public static final String PK_POSTS = "PKPOSTS";
    public static final String PK_USER = "PKUSER";
    public static final String CONTENT = "CONTENT";
    private ActivityReportBinding mBinding;
    private String mPkPosts = "";
    private String mPkUser = "";
    private String mContent = "";
    private String mReportreason = "";

    @Override
    public int getResLayout() {
        return R.layout.activity_report;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityReportBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_reprot);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));

        mBinding.rgContent.setOnCheckedChangeListener((group, checkedId) -> {
            UtilsUi.setVisibility(mBinding.etOtherInfo, checkedId == R.id.rb_other);
            for (int count = 0; count < group.getChildCount(); count++) {
                mReportreason = count + "";
            }
        });
        mBinding.rbOne.setChecked(true);
        mBinding.btnCommit.setOnClickListener(v -> commitReprot());
    }

    private void commitReprot() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        String reportContent = mBinding.etOtherInfo.getText().toString().trim();
        if (mBinding.etOtherInfo.getVisibility() == View.VISIBLE && TextUtils.isEmpty(reportContent)) {
            AppToast.show(mActivity, R.string.toast_report_content);
            return;
        }
        PostreportSaveRequest request = new PostreportSaveRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPkPosts(mPkPosts);
        request.setPkUserpassive(mPkUser);
        request.setReportreason(mReportreason);
        if (!TextUtils.isEmpty(reportContent)) request.setReportcontent(reportContent);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).postreportSaveRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, R.string.toast_report_success);
                        JumpManager.doJumpBack(mActivity);
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
    public void initData() {
        Intent intent = getIntent();
        mPkPosts = intent.getStringExtra(PK_POSTS);
        mPkUser = intent.getStringExtra(PK_USER);
        mContent = intent.getStringExtra(CONTENT);
        mBinding.tvContent.setText(mContent);
    }
}