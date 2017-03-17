package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求提交订单
 */
public class OredrhSaveRequest extends DefaultRequest {
    private String pkBuyer;//购买人
    private List<OrderbsBean> orderbs = new ArrayList<>();

    public String getPkBuyer() {
        return pkBuyer;
    }

    public void setPkBuyer(String pkBuyer) {
        this.pkBuyer = pkBuyer;
    }

    public List<OrderbsBean> getOrderbs() {
        return orderbs;
    }

    public void setOrderbs(List<OrderbsBean> orderbs) {
        this.orderbs = orderbs;
    }

    public static class OrderbsBean {
        private String pkGood;
        private int num;

        public String getPkGood() {
            return pkGood;
        }

        public void setPkGood(String pkGood) {
            this.pkGood = pkGood;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
