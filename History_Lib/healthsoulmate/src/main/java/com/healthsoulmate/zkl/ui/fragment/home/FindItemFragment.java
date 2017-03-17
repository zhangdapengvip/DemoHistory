package com.healthsoulmate.zkl.ui.fragment.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentListItemBinding;
import com.healthsoulmate.zkl.ui.activity.ProductDetailsActivity;
import com.healthsoulmate.zkl.ui.holder.FindListHolder;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.GoodsPageRequest;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListFragment;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsUi;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/19.
 * 发现界面列表
 */
public class FindItemFragment extends AppBaseListFragment {

    private volatile int mPageNum = AppConstants.FIRST_NUM;
    private FragmentListItemBinding mBinding;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_list_item;
    }


    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<ProductBean>(mActivity, listView, AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new FindListHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent detailIntent = new Intent(mActivity, ProductDetailsActivity.class);
                detailIntent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, detailIntent);
            }

            @Override
            public void onLoadMore() {
                requestList();
            }
        };
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        super.initView(view, inflate);
        mBinding = (FragmentListItemBinding) inflate;
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mAdapter && mAdapter.getData().size() <= 0) {
            mRefreshList.perfectPullRefresh();
        }
    }

    private void requestList() {
        GoodsPageRequest request = new GoodsPageRequest();
        request.setType(mArguments.getString(AppConstants.EXTRA_STRING));
        request.setPage(String.valueOf(mPageNum));
        Observable<GoodsPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).goodsPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<GoodsPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(GoodsPageResponse response) {
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

    private void fillList(GoodsPageResponse response) {
        List<ProductBean> list = response.getPage().getRows();
        if (mPageNum == AppConstants.FIRST_NUM) {
            UtilsUi.setVisibility(mBinding.tvEmpty, list.size() <= 0 );
            mAdapter.refreshData(list);
        } else {
            mAdapter.loadData(list);
        }
        mPageNum++;
    }

    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
    }
}
