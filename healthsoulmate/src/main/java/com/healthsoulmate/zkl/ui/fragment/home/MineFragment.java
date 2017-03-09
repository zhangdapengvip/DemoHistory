package com.healthsoulmate.zkl.ui.fragment.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentMineBinding;
import com.healthsoulmate.zkl.forum.activity.ForumHistoryActivity;
import com.healthsoulmate.zkl.forum.activity.MineAttentionActivity;
import com.healthsoulmate.zkl.forum.activity.PostHistoryActivity;
import com.healthsoulmate.zkl.ui.activity.AreadyPayOrderActivity;
import com.healthsoulmate.zkl.ui.activity.LoginActivity;
import com.healthsoulmate.zkl.ui.activity.MineActivity;
import com.healthsoulmate.zkl.ui.activity.WaitPayOrderActivity;
import com.healthsoulmate.zkl.ui.bean.bus.LoginBus;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.QueryUserRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/21.
 * 我的
 */
public class MineFragment extends AppBaseFragment {

    private FragmentMineBinding mBinding;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mBinding = (FragmentMineBinding) inflate;
        mBinding.titleBar.setTitle(R.string.title_mine_fragment);
        mBinding.llUnlogin.setOnClickListener(v -> JumpManager.doJumpForward(mActivity,
                new Intent(mActivity, LoginActivity.class)));
    }

    @Override
    protected void initData() {
        mBinding.btnMine.setOnClickListener(v -> {
            if (UserManager.checkLogin(mActivity)) {
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActivity.class));
            }
        });
        mBinding.btnWaitPay.setOnClickListener(v -> {
            if (UserManager.checkLogin(mActivity))
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, WaitPayOrderActivity.class));
        });
        mBinding.btnAreadyPay.setOnClickListener(v -> {
            if (UserManager.checkLogin(mActivity))
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, AreadyPayOrderActivity.class));
        });
        mBinding.btnMyPost.setOnClickListener(v -> {
            LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
            if (null == loginInfo) return;
            Intent intent = new Intent(mActivity, PostHistoryActivity.class);
            intent.putExtra(AppConstants.EXTRA_STRING, loginInfo.getDatas().getPkUser());
            JumpManager.doJumpForward(mActivity, intent);
        });
        mBinding.btnMySave.setOnClickListener(v -> {
            if (UserManager.checkLogin(mActivity))
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, ForumHistoryActivity.class));
        });
        mBinding.btnMyAttention.setOnClickListener(v -> {
            if (UserManager.checkLogin(mActivity))
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineAttentionActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fillUserInfo();
        notifyUserInfo();
    }

    private void notifyUserInfo() {
        LoginResponse mLoginInfo = UserManager.getLoginInfo(mActivity, false);
        if (null == mLoginInfo) return;
        QueryUserRequest request = new QueryUserRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).queryUserRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        UserManager.storeLoginInfo(UtilsGson.toJson(response));
                        fillUserInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                    }
                }, false);
    }

    private void fillUserInfo() {
        LoginResponse mLoginInfo = UserManager.getLoginInfo(mActivity, false);
        UtilsUi.setVisibility(mBinding.llUnlogin, null == mLoginInfo);
        UtilsUi.setVisibility(mBinding.llLogin, null != mLoginInfo);
        if (null != mLoginInfo) {
            mBinding.setImgInfo(new ImgBinding(mLoginInfo.getDatas().getImage(), R.drawable.ic_default_head,
                    R.dimen.dimen_80));
            mBinding.tvName.setText(mLoginInfo.getDatas().getName());
        }
    }
}
