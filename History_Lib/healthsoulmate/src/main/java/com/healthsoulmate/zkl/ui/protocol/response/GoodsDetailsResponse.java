package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应商品详情
 */
public class GoodsDetailsResponse extends DefaultResponse {

    private ProductBean datas;
    
    public ProductBean getDatas() {
        return datas;
    }

    public void setDatas(ProductBean datas) {
        this.datas = datas;
    }
}
