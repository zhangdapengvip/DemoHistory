package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求商品详情
 */
public class GoodsDetailstRequest extends DefaultRequest {

    private String pkGoods;//商品主键

    public String getPkGoods() {
        return pkGoods;
    }

    public void setPkGoods(String pkGoods) {
        this.pkGoods = pkGoods;
    }
}
