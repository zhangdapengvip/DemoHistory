package com.healthsoulmate.zkl.ui.bean;

/**
 * Created by ZeroAries on 2016/5/30.
 * 修改商品信息
 */
public class ChangeInfo {
    private int buynum;// 购买数量
    private String isBuy;// 是否购买 0非 1是
    private String pkSeller;// 商家主键
    private String pkShoppingcart;// 购物车主键  全选传all

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getPkSeller() {
        return pkSeller;
    }

    public void setPkSeller(String pkSeller) {
        this.pkSeller = pkSeller;
    }

    public String getPkShoppingcart() {
        return pkShoppingcart;
    }

    public void setPkShoppingcart(String pkShoppingcart) {
        this.pkShoppingcart = pkShoppingcart;
    }
}
