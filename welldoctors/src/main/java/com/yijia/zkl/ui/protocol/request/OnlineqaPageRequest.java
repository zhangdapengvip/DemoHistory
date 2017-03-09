package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/21.
 * 视野
 */
public class OnlineqaPageRequest extends DefaultRequest {
    public static final String TYPE_HISTORY = "0";
    public static final String TYPE_ONLINE = "1";
    private String page;
    private String type;//0历史1在线

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
