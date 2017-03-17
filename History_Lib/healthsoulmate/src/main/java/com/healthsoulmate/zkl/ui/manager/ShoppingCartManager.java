package com.healthsoulmate.zkl.ui.manager;

import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.healthsoulmate.zkl.ui.protocol.response.SellerBean;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/5/25.
 * 购物车管理者
 */
public class ShoppingCartManager {

    private static final String TAG_LOCALCART = "tag_localcart";

    public volatile static List<ProductBean> productList = new ArrayList<>();

    public static void notifyCount(TextView textView, int count) {
        textView.setText(getCountString(textView, count));
    }

    public static void notifyLocalCount(TextView textView) {
        int count = 0;
        for (ProductBean item : productList) {
            count += item.getBuynum();
        }
        textView.setText(getCountString(textView, count));
    }

    private static String getCountString(TextView textView, int count) {
        String strCount = "";
        if (count <= 0) {
            textView.setVisibility(View.GONE);
        }
        if (count > 0) {
            textView.setVisibility(View.VISIBLE);
            strCount = count + "";
        }
        if (count > 99) {
            strCount = "99";
        }
        return strCount;
    }

    public static void addProduct(ProductBean productInfo) {
        synchronized (ShoppingCartManager.class) {
            boolean isContains = false;
            for (ProductBean item : productList) {
                if (isSameProduct(item, productInfo)) {
                    int count = item.getBuynum();
                    item.setBuynum(++count);
                    isContains = true;
                    break;
                }
            }
            if (!isContains) productList.add(productInfo);
            saveToLocal();
        }
    }

    public static List<ProductBean> getProductList(List<SellerBean> sellerList) {
        List<ProductBean> productList = new ArrayList<>();
        for (SellerBean sellerInfo : sellerList) {
            for (ProductBean productInfo : sellerInfo.getItems()) {
                productInfo.setPkSeller(sellerInfo.getPkSeller());
                productInfo.setSellername(sellerInfo.getSellerName());
                productList.add(productInfo);
            }
        }
        return productList;
    }

    public static void asyncFromNet(List<SellerBean> sellerList) {
        productList.clear();
        for (SellerBean sellerInfo : sellerList) {
            for (ProductBean productInfo : sellerInfo.getItems()) {
                productInfo.setPkSeller(sellerInfo.getPkSeller());
                productInfo.setSellername(sellerInfo.getSellerName());
                productList.add(productInfo);
            }
        }
        saveToLocal();
    }

    public static boolean isSameProduct(ProductBean one, ProductBean two) {
        return one.getPkSeller().equals(two.getPkSeller())
                && one.getPkGoods().equals(two.getPkGoods());
    }

    public static void saveToLocal() {
        String localCart = UtilsGson.toJson(productList);
        UtilsSharedPreference.setStringValue(TAG_LOCALCART, localCart);
    }

    public static void clearLocal() {
        UtilsSharedPreference.setStringValue(TAG_LOCALCART, "");
        productList.clear();
    }

    public static void initFromLocal() {
        Type type = new TypeToken<List<ProductBean>>() {
        }.getType();
        String localCart = UtilsSharedPreference.getStringValue(TAG_LOCALCART);
        List<ProductBean> localList = UtilsGson.fromJson(localCart, type);
        if (null != localList) productList = localList;
    }

}
