package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 同问题计数
 */
public class QusetionUpdateRequest extends DefaultRequest {
    private String pkQuestion;//问题主键
    private String pkUser;//用户主键

    public String getPkQuestion() {
        return pkQuestion;
    }

    public void setPkQuestion(String pkQuestion) {
        this.pkQuestion = pkQuestion;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }
}
