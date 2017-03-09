package com.healthsoulmate.zkl.ui.protocol.response;

import java.util.List;

/**
 * Created by ZeroAries on 2016/5/30.
 * 卖家信息
 */
public class SellerBean {
    private String fee;// 卖家总价
    private String pkSeller;// 卖家主键
    private String sellerName;// 卖家名
    private List<ProductBean> items;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPkSeller() {
        return pkSeller;
    }

    public void setPkSeller(String pkSeller) {
        this.pkSeller = pkSeller;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<ProductBean> getItems() {
        return items;
    }

    public void setItems(List<ProductBean> items) {
        this.items = items;
    }
}