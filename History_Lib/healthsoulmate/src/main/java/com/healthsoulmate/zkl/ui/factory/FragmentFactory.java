package com.healthsoulmate.zkl.ui.factory;

import android.os.Bundle;

import com.healthsoulmate.zkl.forum.fragment.ForumHomeFragment;
import com.healthsoulmate.zkl.ui.fragment.home.FindItemFragment;
import com.healthsoulmate.zkl.ui.fragment.home.FindListFragment;
import com.healthsoulmate.zkl.ui.fragment.home.HomeListFragment;
import com.healthsoulmate.zkl.ui.fragment.home.MineFragment;
import com.healthsoulmate.zkl.ui.fragment.home.ShoppingCartFragment;
import com.healthsoulmate.zkl.ui.fragment.product.EvaluateListFragment;
import com.healthsoulmate.zkl.ui.fragment.product.ProductDetailFragment;
import com.healthsoulmate.zkl.ui.fragment.product.ProductEvaluateListFragment;
import com.healthsoulmate.zkl.ui.fragment.product.ProductSummaryFragment;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsTypePageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.uibase.AppPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/4/21.
 * Fragment工厂类
 */
public class FragmentFactory {
    private static ArrayList<AppBaseFragment> mHomeFragments = new ArrayList<>();

    /**
     * 首页Fragment
     */
    public static List<AppBaseFragment> createHomeFragments() {
        mHomeFragments.clear();
        mHomeFragments.add(new HomeListFragment());
        mHomeFragments.add(new ForumHomeFragment());
        mHomeFragments.add(new FindListFragment());
        mHomeFragments.add(new ShoppingCartFragment());
        mHomeFragments.add(new MineFragment());
        return mHomeFragments;
    }

    private static ArrayList<AppPagerFragment> mProductFragments = new ArrayList<>();

    /**
     * 商品详情Fragment
     *
     * @param detailInfo
     */
    public static List<AppPagerFragment> createProductFragments(ProductBean detailInfo) {
        mProductFragments.clear();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.PARCELABLE_KEY, detailInfo);

        ProductSummaryFragment summaryFragment = new ProductSummaryFragment();
        summaryFragment.setArguments(bundle);
        mProductFragments.add(summaryFragment);

        ProductDetailFragment detailFragment = new ProductDetailFragment();
        detailFragment.setArguments(bundle);
        mProductFragments.add(detailFragment);

        ProductEvaluateListFragment evaluateListFragment = new ProductEvaluateListFragment();
        evaluateListFragment.setArguments(bundle);
        mProductFragments.add(evaluateListFragment);
        return mProductFragments;
    }

    private static ArrayList<AppBaseFragment> mFindItemFragments = new ArrayList<>();

    /**
     * 发现Fragment
     *
     * @param itemList 标签列表
     * @return
     */
    public static List<AppBaseFragment> createFindItemFragments(List<GoodsTypePageResponse.DatasBean> itemList) {
        mFindItemFragments.clear();
        int maxCount = itemList.size();
        for (int count = 0; count < maxCount; count++) {
            FindItemFragment fragment = new FindItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.EXTRA_STRING, itemList.get(count).getPkGoodstype());
            fragment.setArguments(bundle);
            mFindItemFragments.add(fragment);
        }
        return mFindItemFragments;
    }

    private static ArrayList<AppBaseFragment> mEvaluateFragments = new ArrayList<>();

    /**
     * 发现Fragment
     *
     * @param maxCount
     */
    public static List<AppBaseFragment> createEvaluateFragments(ProductBean productInfo, int maxCount) {
        mEvaluateFragments.clear();
        for (int count = 0; count < maxCount; count++) {
            EvaluateListFragment fragment = new EvaluateListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.PARCELABLE_KEY, productInfo);
            bundle.putString(AppConstants.EXTRA_STRING, count + "");
            fragment.setArguments(bundle);
            mEvaluateFragments.add(fragment);
        }
        return mEvaluateFragments;
    }
}