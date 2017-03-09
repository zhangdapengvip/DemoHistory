package com.healthsoulmate.zkl.ui.fragment.product;

import android.widget.ListView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.ui.holder.ProductEvaluateHolder;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.CommentPageRequest;
import com.healthsoulmate.zkl.ui.protocol.response.CommentPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppBaseListFragment;
import com.zero.library.base.uibase.DefaultAdapter;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/20.
 * 评价列表
 */
public class EvaluateListFragment extends AppBaseListFragment {

    private String mType;
    private ProductBean mProductInfo;
    private int mPageNum = AppConstants.FIRST_NUM;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_list_item;
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<CommentPageResponse.PageBean.RowsBean>(mActivity, listView, AppConstants.PAGE_COUNT) {
            @Override
            protected AppBaseHolder getHolder() {
                return new ProductEvaluateHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                requestList();
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        requestList();
    }

    @Override
    protected void initData() {
        mType = mArguments.getString(AppConstants.EXTRA_STRING);
        mProductInfo = mArguments.getParcelable(AppConstants.PARCELABLE_KEY);
        mRefreshList.perfectPullRefresh();
    }

    private void requestList() {
        CommentPageRequest request = new CommentPageRequest();
        request.setScoreType(mType);
        request.setPkGoods(mProductInfo.getPkGoods());
        Observable<CommentPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).commentPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<CommentPageResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(CommentPageResponse response) {
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

    private void fillList(CommentPageResponse response) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        mPageNum++;
    }
}
