package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 资讯详情评论
 */
public class InfodiscussSaveRequest extends DefaultRequest {
    private String pkInfomation;//资讯主键
    private String pkUser;//用户主键
    private String content;//评论内容

    public String getPkInfomation() {
        return pkInfomation;
    }

    public void setPkInfomation(String pkInfomation) {
        this.pkInfomation = pkInfomation;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
