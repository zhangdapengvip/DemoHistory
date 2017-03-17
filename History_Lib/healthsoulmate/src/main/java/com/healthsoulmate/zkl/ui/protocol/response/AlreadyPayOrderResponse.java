package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应已支付订单
 */
public class AlreadyPayOrderResponse extends DefaultResponse {

    private List<ProductBean> list;

    public List<ProductBean> getList() {
        return list;
    }

    public void setList(List<ProductBean> list) {
        this.list = list;
    }
}
