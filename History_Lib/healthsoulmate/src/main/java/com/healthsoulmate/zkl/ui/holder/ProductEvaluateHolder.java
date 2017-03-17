package com.healthsoulmate.zkl.ui.holder;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ItemEvaluateImageviewBinding;
import com.healthsoulmate.zkl.databinding.ItemProductEvaluateBinding;
import com.healthsoulmate.zkl.ui.protocol.response.CommentPageResponse;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/4/25.
 * 商品评价
 */
public class ProductEvaluateHolder extends AppBaseHolder<CommentPageResponse.PageBean.RowsBean> {

    private ItemProductEvaluateBinding mBinding;

    public ProductEvaluateHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.item_product_evaluate;
    }

    @Override
    public void refreshView(int position) {
        CommentPageResponse.PageBean.RowsBean data = getData();
        UtilsUi.setRatingLayoutParams(mBinding.ratingEvaluate);
        mBinding.ratingEvaluate.setRating(data.getScore());
        mBinding.tvEvaluateContent.setText(data.getContent());
        mBinding.tvName.setText(data.getUserName());
        mBinding.tvEvaluateTime.setText(data.getCommenttime());
        mBinding.tvReply.setText(mActivity.getString(R.string.format_reply, data.getReply()));
        UtilsUi.setVisibility(mBinding.tvReply, !TextUtils.isEmpty(data.getReply()));

        mBinding.setImgInfoHead(new ImgBinding(data.getUserImage(), R.drawable.ic_default_head, R.dimen.dimen_25));

        List<String> evalueList = new ArrayList<>();
        evalueList.add("http://f.hiphotos.baidu" +
                ".com/image/h%3D200/sign=7fd74f14afd3fd1f2909a53a004e25ce" +
                "/c995d143ad4bd1138eac6b085dafa40f4bfb05a3.jpg");
        evalueList.add("http://f.hiphotos.baidu" +
                ".com/image/h%3D200/sign=7fd74f14afd3fd1f2909a53a004e25ce" +
                "/c995d143ad4bd1138eac6b085dafa40f4bfb05a3.jpg");
        evalueList.add("http://f.hiphotos.baidu" +
                ".com/image/h%3D200/sign=7fd74f14afd3fd1f2909a53a004e25ce" +
                "/c995d143ad4bd1138eac6b085dafa40f4bfb05a3.jpg");
//        fillEvaluateImg(evalueList);
    }

    private void fillEvaluateImg(List<String> evalueList) {
        if (evalueList.size() <= 0) return;
        mBinding.llEvaluateContent.removeAllViews();
        for (String url : evalueList) {
            ItemEvaluateImageviewBinding inflate = DataBindingUtil.inflate(
                    LayoutInflater.from(mActivity), R.layout.item_evaluate_imageview, null, false);
            inflate.setImgEva(new ImgBinding(url, R.drawable.ic_default_head, R.dimen.dimen_60));
            mBinding.llEvaluateContent.addView(inflate.getRoot());
        }
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (ItemProductEvaluateBinding) viewDataBinding;
    }
}
