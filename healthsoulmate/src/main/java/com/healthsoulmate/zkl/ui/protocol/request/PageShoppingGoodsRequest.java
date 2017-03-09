package com.healthsoulmate.zkl.ui.protocol.request;

import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.zero.library.base.retrofit.DefaultRequest;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求查询购物车
 */
public class PageShoppingGoodsRequest extends DefaultRequest {

    private String pkUser;//购买人
    private List<ProductBean> items;

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public List<ProductBean> getItems() {
        return items;
    }

    public void setItems(List<ProductBean> items) {
        this.items = items;
    }
}
