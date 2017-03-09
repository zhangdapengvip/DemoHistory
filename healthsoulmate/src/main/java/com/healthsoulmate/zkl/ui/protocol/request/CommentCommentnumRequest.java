package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求评价计数接口
 */
public class CommentCommentnumRequest extends DefaultRequest {
    private String pkGoods;

    public String getPkGoods() {
        return pkGoods;
    }

    public void setPkGoods(String pkGoods) {
        this.pkGoods = pkGoods;
    }
}