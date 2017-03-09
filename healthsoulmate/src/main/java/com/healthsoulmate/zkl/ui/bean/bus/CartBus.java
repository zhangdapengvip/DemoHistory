package com.healthsoulmate.zkl.ui.bean.bus;

/**
 * Created by ZeroAries on 2016/4/27.
 * 购物车总线
 */
public class CartBus {
    public CartBus(int totalNum) {
        this.totalNum = totalNum;
    }

    private int totalNum;//总数

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
