package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求删除条目
 */
public class ShoppingcartRemoveRequest extends DefaultRequest {
    private String pkUser;// 购买人
    private String pkShoppingcart;// 购物车主键

    public String getPkShoppingcart() {
        return pkShoppingcart;
    }

    public void setPkShoppingcart(String pkShoppingcart) {
        this.pkShoppingcart = pkShoppingcart;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }
}
