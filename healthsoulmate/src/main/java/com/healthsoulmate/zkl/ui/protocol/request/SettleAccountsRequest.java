package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求结算订单
 */
public class SettleAccountsRequest extends DefaultRequest {

    private String pkBuyer;//购买人
    private String orderNo;//单号

    public String getPkBuyer() {
        return pkBuyer;
    }

    public void setPkBuyer(String pkBuyer) {
        this.pkBuyer = pkBuyer;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
