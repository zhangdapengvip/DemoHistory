package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.databinding.tool.MergedBinding;
import android.text.TextUtils;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityEvaluateOrderBinding;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.CommentSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
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
 * Created by ZeroAries on 2016/5/30.
 * 订单评价
 */
public class EvaluateOrderActivity extends AppBaseActivity {

    private ActivityEvaluateOrderBinding mBinding;
    private ProductBean mProductInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_evaluate_order;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        Intent intent = getIntent();
        mProductInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mProductInfo) JumpManager.doJumpBack(mActivity);

        mBinding = (ActivityEvaluateOrderBinding) viewDataBinding;
        UtilsUi.setRatingLayoutParams(mBinding.ratingEvaluate);
        mBinding.setImgInfo(new ImgBinding(mProductInfo.getImageurl(), R.drawable.ic_default_img, R.dimen.dimen_60,
                10));
        mBinding.titleBar.setTitle(R.string.title_evaluate_order);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText(R.string.commit);
        mBinding.titleBar.setRightListener(v -> commitEvaluate());
    }

    @Override
    public void initData() {
    }

    private void commitEvaluate() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        String content = mBinding.etEvaluateContent.getText().toString().trim();
        int rating = (int) mBinding.ratingEvaluate.getRating();
        if (TextUtils.isEmpty(content)) {
            AppToast.show(mActivity, R.string.toast_evaluate_empty);
            return;
        }
        CommentSaveRequest request = new CommentSaveRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setAnonymous(mBinding.cbAnonymous.isChecked() ?
                CommentSaveRequest.IS_ANONYMOUS :
                CommentSaveRequest.UN_ANONYMOUS);
        request.setContent(content);
        request.setPkOrdergoods(mProductInfo.getPkGoods());
        request.setScore(String.valueOf(rating));
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).commentSaveRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.titleBar.getRightView(),
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, R.string.toast_evaluate_success);
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
}
