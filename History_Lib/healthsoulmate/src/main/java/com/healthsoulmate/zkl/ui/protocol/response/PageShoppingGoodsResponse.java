package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应查询购物车
 */
public class PageShoppingGoodsResponse extends DefaultResponse {

    private DatasBean datas;

    public static class DatasBean {
        private String pkUser;// 购买者
        private String totalMoney;// 总价
        private List<SellerBean> sellers;
        private int countNum;

        public String getPkUser() {
            return pkUser;
        }

        public void setPkUser(String pkUser) {
            this.pkUser = pkUser;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public List<SellerBean> getSellers() {
            return sellers;
        }

        public void setSellers(List<SellerBean> sellers) {
            this.sellers = sellers;
        }

        public int getCountNum() {
            return countNum;
        }

        public void setCountNum(int countNum) {
            this.countNum = countNum;
        }
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }
}
