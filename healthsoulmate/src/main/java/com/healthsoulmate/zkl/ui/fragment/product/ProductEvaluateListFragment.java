package com.healthsoulmate.zkl.ui.fragment.product;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.RadioGroup;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentProductEvaluateBinding;
import com.healthsoulmate.zkl.ui.factory.FragmentFactory;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.CommentCommentnumRequest;
import com.healthsoulmate.zkl.ui.protocol.response.CommentCommentnumResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.FragmentManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppPagerFragment;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/22.
 * 商品评价
 */
public class ProductEvaluateListFragment extends AppPagerFragment {

    private FragmentProductEvaluateBinding mBinding;
    private ProductBean mProductInfo;

    @Override
    public String getPagerTitle() {
        return "评价";
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_product_evaluate;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mProductInfo = mArguments.getParcelable(AppConstants.PARCELABLE_KEY);
        mBinding = (FragmentProductEvaluateBinding) inflate;
        new FragmentManager(getActivity(), FragmentFactory.createEvaluateFragments(mProductInfo,
                mBinding.rgTabs.getChildCount()), R.id.tab_evaluate_content, mBinding.rgTabs) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                super.onCheckedChanged(radioGroup, checkedId);

            }
        };
    }

    @Override
    protected void initData() {
        CommentCommentnumRequest request = new CommentCommentnumRequest();
        request.setPkGoods(mProductInfo.getPkGoods());
        Observable<CommentCommentnumResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .commentCommentnumRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<CommentCommentnumResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(CommentCommentnumResponse response) {
                        CommentCommentnumResponse.DatasBean datas = response.getDatas();
                        mBinding.rbTabOne.setText("全部评价\n" + datas.getCommentnum());
                        mBinding.rbTabTwo.setText("好评 \n" + datas.getHpnum());
                        mBinding.rbTabThree.setText("中评 \n" + datas.getZpnum());
                        mBinding.rbTabFour.setText("差评 \n" + datas.getCpnum());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }
}
