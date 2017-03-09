package com.healthsoulmate.zkl.ui.fragment.product;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentProductSummaryBinding;
import com.healthsoulmate.zkl.databinding.ItemEvaluateImageviewBinding;
import com.healthsoulmate.zkl.databinding.ItemImagePagerBinding;
import com.healthsoulmate.zkl.databinding.ItemSummaryEvaluateBinding;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.CommentPageRequest;
import com.healthsoulmate.zkl.ui.protocol.request.GoodsDetailstRequest;
import com.healthsoulmate.zkl.ui.protocol.response.CommentPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsDetailsResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppPagerAdapter;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/22.
 * 商品简介
 */
public class ProductSummaryFragment extends AppPagerFragment {

    private FragmentProductSummaryBinding mBinding;
    private ProductBean mProductInfo;

    @Override
    public String getPagerTitle() {
        return "商品";
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_product_summary;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mProductInfo = mArguments.getParcelable(AppConstants.PARCELABLE_KEY);
        mBinding = (FragmentProductSummaryBinding) inflate;
        RelativeLayout.LayoutParams paramsViewPager = new RelativeLayout.LayoutParams(
                UtilsUi.getScreenWidth(mActivity),
                UtilsUi.getScreenWidth(mActivity));
        mBinding.vpHead.setLayoutParams(paramsViewPager);
    }

    @Override
    protected void initData() {
        requestProductInfo();
        requestEvaluateInfo();
    }

    private void requestEvaluateInfo() {
        CommentPageRequest request = new CommentPageRequest();
        request.setScoreType("0");
        request.setPkGoods(mProductInfo.getPkGoods());
        Observable<CommentPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).commentPageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<CommentPageResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(CommentPageResponse response) {
                        fillEvaluate(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                    }
                });
    }

    private void fillEvaluate(CommentPageResponse response) {
        List<CommentPageResponse.PageBean.RowsBean> evaluateList = response.getPage().getRows();
        mBinding.llEvaluateContent.removeAllViews();
        for (int position = 0; position < evaluateList.size() && position < 3; position++) {
            CommentPageResponse.PageBean.RowsBean evaluateInfo = evaluateList.get(position);
            ItemSummaryEvaluateBinding inflate = DataBindingUtil.inflate(
                    LayoutInflater.from(mActivity), R.layout.item_summary_evaluate, null, false);
            UtilsUi.setRatingLayoutParams(inflate.ratingBar);
            //TODO 绑定数据
            inflate.tvEvaluateContent.setText(evaluateInfo.getContent());
            inflate.tvName.setText(evaluateInfo.getUserName());
            inflate.ratingBar.setRating(evaluateInfo.getScore());

//            List<String> evalueList = new ArrayList<>();
//            evalueList.add("http://f.hiphotos.baidu" +
//                    ".com/image/h%3D200/sign=7fd74f14afd3fd1f2909a53a004e25ce" +
//                    "/c995d143ad4bd1138eac6b085dafa40f4bfb05a3.jpg");
//            evalueList.add("http://f.hiphotos.baidu" +
//                    ".com/image/h%3D200/sign=7fd74f14afd3fd1f2909a53a004e25ce" +
//                    "/c995d143ad4bd1138eac6b085dafa40f4bfb05a3.jpg");
//            evalueList.add("http://f.hiphotos.baidu" +
//                    ".com/image/h%3D200/sign=7fd74f14afd3fd1f2909a53a004e25ce" +
//                    "/c995d143ad4bd1138eac6b085dafa40f4bfb05a3.jpg");
//            fillEvaluateImg(inflate.llImgContent, evalueList);

            mBinding.llEvaluateContent.addView(inflate.getRoot());
        }
    }

    private void fillEvaluateImg(LinearLayout viewParent, List<String> evalueList) {
        if (evalueList.size() <= 0) return;
        viewParent.removeAllViews();
        for (String url : evalueList) {
            ItemEvaluateImageviewBinding inflate = DataBindingUtil.inflate(
                    LayoutInflater.from(mActivity), R.layout.item_evaluate_imageview, null, false);
            inflate.setImgEva(new ImgBinding(url, R.drawable.ic_default_head, R.dimen.dimen_60));
            viewParent.addView(inflate.getRoot());
        }
    }


    private void requestProductInfo() {
        GoodsDetailstRequest request = new GoodsDetailstRequest();
        request.setPkGoods(mProductInfo.getPkGoods());
        Observable<GoodsDetailsResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                goodsDetailsRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<GoodsDetailsResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(GoodsDetailsResponse request) {
                        fillView(request);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    private void fillView(GoodsDetailsResponse request) {
        ProductBean productInfo = request.getDatas();
        mBinding.tvTitle.setText(productInfo.getGoodsname());
        mBinding.tvIntroduce.setText(productInfo.getDescription());
        String productImgs = productInfo.getGoddsImages();
        List<String> imgLists = Arrays.asList(productImgs.split(","));
        fillViewPager(imgLists);
        refreshPrice(mActivity.getString(R.string.format_price, productInfo.getPrice()));
        mBinding.tvEvaluateCount.setText(mActivity.getString(R.string.format_evaluate_counts,
                productInfo.getCommentsnum()));
        mBinding.tvGoodRate.setText(mActivity.getString(R.string.format_good_rate, productInfo.getReputably()) + "%");
    }

    private void fillViewPager(final List<String> imgList) {
        refreshCount("1/" + imgList.size());
        mBinding.vpHead.setAdapter(new AppPagerAdapter<String>(imgList) {
            @Override
            protected View initItemView(String s, int position) {
                ItemImagePagerBinding itemInflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                        R.layout.item_image_pager, null, false);
                itemInflate.setImgInfo(new ImgBinding(s, R.drawable.ic_default_head, R.dimen.dimen_320));
                return itemInflate.getRoot();
            }
        });
        mBinding.vpHead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshCount((position + 1) + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void refreshCount(String countStr) {
        CharSequence charSequence = UtilsString.textLadderString(countStr, "/", 1.2f, 0.9f);
        mBinding.tvCount.setText(charSequence);
    }

    private void refreshPrice(String countStr) {
        CharSequence charSequence = UtilsString.textLadderString(countStr, ".", 1.0f, 0.8f);
        mBinding.tvPrice.setText(charSequence);
    }
}
