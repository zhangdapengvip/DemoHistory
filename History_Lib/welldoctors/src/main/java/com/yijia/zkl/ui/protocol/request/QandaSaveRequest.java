package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 提问
 */
public class QandaSaveRequest extends DefaultRequest {
    private String pkUser;//用户主键
    private String pkOnlineqa;//在线问答主键
    private String question;//问题

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkOnlineqa() {
        return pkOnlineqa;
    }

    public void setPkOnlineqa(String pkOnlineqa) {
        this.pkOnlineqa = pkOnlineqa;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
