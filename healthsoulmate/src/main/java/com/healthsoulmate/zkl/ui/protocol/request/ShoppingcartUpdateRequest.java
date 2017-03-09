package com.healthsoulmate.zkl.ui.protocol.request;

import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.retrofit.DefaultRequest;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求修改数量
 */
public class ShoppingcartUpdateRequest extends DefaultRequest {

    private String pkUser;// 购买人
    private int buynum;// 购买数量
    private String isBuy;// 是否购买 0非 1是
    private String pkSeller;// 商家主键
    private String pkShoppingcart;// 购物车主键  全选传all
    private List<ProductBean> items;

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public String getPkSeller() {
        return pkSeller;
    }

    public void setPkSeller(String pkSeller) {
        this.pkSeller = pkSeller;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getPkShoppingcart() {
        return pkShoppingcart;
    }

    public void setPkShoppingcart(String pkShoppingcart) {
        this.pkShoppingcart = pkShoppingcart;
    }

    public List<ProductBean> getItems() {
        return items;
    }

    public void setItems(List<ProductBean> items) {
        this.items = items;
    }
}
