package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 广告栏
 */
public class InformationHeadResponse extends DefaultResponse {
    private List<NewsPage> list;

    public List<NewsPage> getList() {
        return list;
    }

    public void setList(List<NewsPage> datas) {
        this.list = datas;
    }
}
