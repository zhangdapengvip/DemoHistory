package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityBlockIntroduceBinding;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.FavoriteRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.healthsoulmate.zkl.forum.protocol.response.FavoriteResponse;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.binding.ImgBinding;
import com.zero.library.base.view.AppProgressDialog;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/9.
 * 板块介绍
 */
public class BlockIntroduceActivity extends AppBaseActivity {

    private DiscuzsectionPageResponse.ListBean mBlockInfo;
    private ActivityBlockIntroduceBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_block_introduce;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        Intent intent = getIntent();
        mBlockInfo = intent.getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mBlockInfo) JumpManager.doJumpBack(mActivity);

        mBinding = (ActivityBlockIntroduceBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_block_introduce);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }


    private void refreshButton(DiscuzsectionPageResponse.ListBean data) {
        if (Constants.YES.equals(data.getIsFav())) {
            mBinding.btnStore.setText(R.string.string_aready_store);
            mBinding.btnStore.setBackgroundResource(R.drawable.bg_btn_gray);
        } else {
            mBinding.btnStore.setText(R.string.string_store);
            mBinding.btnStore.setBackgroundResource(R.drawable.bg_btn_normal);
        }
    }

    @Override
    public void initData() {
        mBinding.setImgInfo(new ImgBinding(mBlockInfo.getImageurl(),
                R.drawable.ic_default_head, R.dimen.dimen_55));
        mBinding.setBlockInfo(mBlockInfo);
        mBinding.btnStore.setOnClickListener(v -> doAttention(mBlockInfo));
        refreshButton(mBlockInfo);
    }

    private void doAttention(DiscuzsectionPageResponse.ListBean data) {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        FavoriteRequest request = new FavoriteRequest();
        request.setFlag(Constants.YES.equals(data.getIsFav()) ? FavoriteRequest.DELETE : FavoriteRequest.ADD);
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPkDiscuzsection(data.getPkDiscuzsection());
        request.setPkSectionfavorites(data.getPkSectionfavorites());
        Observable<FavoriteResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).favoriteRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.btnStore,
                new RetrofitUtils.ResponseListener<FavoriteResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(FavoriteResponse favoriteResponse) {
                        if (Constants.YES.equals(data.getIsFav())) {
                            data.setIsFav(Constants.NO);
                            AppToast.show(mActivity, R.string.toast_unstore_success);
                        } else {
                            data.setIsFav(Constants.YES);
                            AppToast.show(mActivity, R.string.toast_store_success);
                        }
                        refreshButton(data);
                        RxBus.getInstance().send(favoriteResponse);
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