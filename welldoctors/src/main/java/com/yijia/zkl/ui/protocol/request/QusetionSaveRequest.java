package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 医友提问
 */
public class QusetionSaveRequest extends DefaultRequest {
    private String pkView;//视野主键
    private String pkUser;//用户主键
    private String questcontent;//提问内容

    public String getPkView() {
        return pkView;
    }

    public void setPkView(String pkView) {
        this.pkView = pkView;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getQuestcontent() {
        return questcontent;
    }

    public void setQuestcontent(String questcontent) {
        this.questcontent = questcontent;
    }
}
