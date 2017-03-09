package com.healthsoulmate.zkl.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityTitleListBinding;
import com.healthsoulmate.zkl.ui.holder.WaitPayOrderHolder;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.OredrhPageRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.OredrhPageResponse;
import com.zero.library.base.constants.AppConstants;
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
 * 待付款
 */
public class WaitPayOrderActivity extends AppBaseListActivity {

    private ActivityTitleListBinding mBinding;

    @Override
    public int getResLayout() {
        return R.layout.activity_title_list;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        super.initView(viewDataBinding);
        mBinding = (ActivityTitleListBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_wait_pay_order);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<OredrhPageResponse.ListBean>(mActivity, listView) {
            @Override
            protected AppBaseHolder getHolder() {
                return new WaitPayOrderHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, OrderDetailActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        requestList();
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
    }

    private void requestList() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) {
            pullDownComplete();
            return;
        }
        OredrhPageRequest request = new OredrhPageRequest();
        request.setPkBuyer(loginInfo.getDatas().getPkUser());
        Observable<OredrhPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).oredrhPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<OredrhPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(OredrhPageResponse response) {
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

    private void fillList(OredrhPageResponse response) {
        List<OredrhPageResponse.ListBean> list = response.getList();
        UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }
}
