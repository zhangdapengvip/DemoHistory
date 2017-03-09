package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求待支付订单
 */
public class OredrhPageRequest extends DefaultRequest {

    private String pkBuyer;

    public String getPkBuyer() {
        return pkBuyer;
    }

    public void setPkBuyer(String pkBuyer) {
        this.pkBuyer = pkBuyer;
    }
}
