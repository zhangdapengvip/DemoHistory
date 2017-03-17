package com.healthsoulmate.zkl.ui.activity;

import android.databinding.ViewDataBinding;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityTitleListBinding;
import com.healthsoulmate.zkl.ui.holder.AreadyPayOrderHolder;
import com.healthsoulmate.zkl.ui.holder.WaitPayOrderHolder;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.AlreadyPayOrderRequest;
import com.healthsoulmate.zkl.ui.protocol.request.OredrhPageRequest;
import com.healthsoulmate.zkl.ui.protocol.response.AlreadyPayOrderResponse;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.OredrhPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListActivity;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/26.
 * 已付款
 */
public class AreadyPayOrderActivity extends AppBaseListActivity {

    private ActivityTitleListBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_title_list;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityTitleListBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_aready_pay_order);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter(mActivity, listView) {
            @Override
            protected AppBaseHolder<ProductBean> getHolder() {
                return new AreadyPayOrderHolder(mActivity);
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        requestList();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshList.perfectPullRefresh();
    }

    private void requestList() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) {
            pullDownComplete();
            return;
        }
        AlreadyPayOrderRequest request = new AlreadyPayOrderRequest();
        request.setPkBuyer(loginInfo.getDatas().getPkUser());
        Observable<AlreadyPayOrderResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .alreadyPayOrderRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<AlreadyPayOrderResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(AlreadyPayOrderResponse response) {
                        fillList(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillList(AlreadyPayOrderResponse response) {
        List<ProductBean> list = response.getList();
        UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }
}
