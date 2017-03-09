package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityForumPeopleBinding;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.UserfocusSaveRequest;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.QueryUserRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/2.
 * 发帖人信息
 */
public class ForumPeopleActivity extends AppBaseActivity {

    private ActivityForumPeopleBinding mBinding;
    private String mPkUser;

    @Override
    public int getResLayout() {
        return R.layout.activity_forum_people;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityForumPeopleBinding) viewDataBinding;
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        Intent intent = getIntent();
        mPkUser = intent.getStringExtra(AppConstants.EXTRA_STRING);
        if (TextUtils.isEmpty(mPkUser)) JumpManager.doJumpBack(mActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void initData() {
    }

    private void requestData() {
        QueryUserRequest request = new QueryUserRequest();
        request.setPkUser(mPkUser);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).queryUserRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        fillView(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        JumpManager.doJumpBack(mActivity);
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    private void fillView(LoginResponse response) {
        LoginResponse.DatasBean otherInfo = response.getDatas();
        mBinding.setImgInfo(new ImgBinding(otherInfo.getImage(), R.drawable.ic_default_head, R.dimen.dimen_45, 10));
        mBinding.tvName.setText(otherInfo.getName());
        mBinding.btnAttention.setOnClickListener(v -> doAttention(response));
        mBinding.btnMyPost.setOnClickListener(v -> {
            String pkUser = response.getDatas().getPkUser();
            if (!TextUtils.isEmpty(pkUser)) {
                Intent intent = new Intent(mActivity, PostHistoryActivity.class);
                intent.putExtra(AppConstants.EXTRA_STRING, pkUser);
                JumpManager.doJumpForward(mActivity, intent);
            }
        });
        if (Constants.YES.equals(otherInfo.getIsFollow())) {
            mBinding.btnAttention.setText(R.string.string_aready_attention);
            mBinding.btnAttention.setTextColor(UtilsUi.getColor(R.color.bg_text_hint));
            mBinding.btnAttention.setBackgroundResource(R.drawable.bg_btn_attention);
        } else {
            mBinding.btnAttention.setText(R.string.string_attention);
            mBinding.btnAttention.setTextColor(UtilsUi.getColor(R.color.bg_app_text_color));
            mBinding.btnAttention.setBackgroundResource(R.drawable.bg_btn_unattention);
        }
    }

    private void doAttention(LoginResponse otherResponse) {
        LoginResponse.DatasBean otherInfo = otherResponse.getDatas();
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        String pkUser = loginInfo.getDatas().getPkUser();
        String otherPk = otherInfo.getPkUser();
        if (pkUser.equals(otherPk)) {
            AppToast.show(mActivity, R.string.notice_attention_self);
            return;
        }
        UserfocusSaveRequest request = new UserfocusSaveRequest();
        request.setPkUser(pkUser);
        request.setPkUserbefocus(otherPk);
        request.setFlag(Constants.YES.equals(otherInfo.getIsFollow()) ?
                UserfocusSaveRequest.DELETE :
                UserfocusSaveRequest.ADD);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).userfocusSaveRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnAttention,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        otherInfo.setIsFollow(UserfocusSaveRequest.ADD.equals(request.getFlag()) ?
                                Constants.YES : Constants.NO);
                        fillView(otherResponse);
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
}