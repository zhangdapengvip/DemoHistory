package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求评价列表
 */
public class CommentPageRequest extends DefaultRequest {

    private String scoreType;//0查看全部1好评2中评3差评
    private String pkGoods;//商品主键

    public String getPkGoods() {
        return pkGoods;
    }

    public void setPkGoods(String pkOdergoods) {
        this.pkGoods = pkOdergoods;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }
}