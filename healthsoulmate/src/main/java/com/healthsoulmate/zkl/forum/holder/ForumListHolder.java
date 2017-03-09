package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderForumListBinding;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.FavoriteRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.healthsoulmate.zkl.forum.protocol.response.FavoriteResponse;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.binding.ImgBinding;
import com.zero.library.base.view.AppProgressDialog;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/27.
 * 论坛列表
 */
public class ForumListHolder extends AppBaseHolder<DiscuzsectionPageResponse.ListBean> {

    private HolderForumListBinding mBinding;
    protected AppProgressDialog mProgressDialog;

    public ForumListHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_forum_list;
    }

    @Override
    public void refreshView(int position) {
        DiscuzsectionPageResponse.ListBean data = getData();
        mBinding.setImgInfo(new ImgBinding(data.getImageurl(), R.drawable.ic_default_img, R.dimen.dimen_55));
        mBinding.setItemInfo(data);
        mBinding.tvAttention.setOnClickListener(v -> doAttention(data));
        refreshStar(data);
    }

    private void refreshStar(DiscuzsectionPageResponse.ListBean data) {
        mBinding.tvAttention.setBackgroundResource(Constants.YES.equals(data.getIsFav())
                ? R.drawable.ic_attention_checked : R.drawable.ic_attention_normal);
    }

    private void doAttention(DiscuzsectionPageResponse.ListBean data) {
        mProgressDialog = new AppProgressDialog(mActivity);
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        FavoriteRequest request = new FavoriteRequest();
        request.setFlag(Constants.YES.equals(data.getIsFav()) ? FavoriteRequest.DELETE : FavoriteRequest.ADD);
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPkDiscuzsection(data.getPkDiscuzsection());
        request.setPkSectionfavorites(data.getPkSectionfavorites());
        Observable<FavoriteResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class).favoriteRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.tvAttention,
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
                        refreshStar(data);
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

    public void showProgressDialog() {
        if (null != mProgressDialog && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hidenProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderForumListBinding) viewDataBinding;
    }
}