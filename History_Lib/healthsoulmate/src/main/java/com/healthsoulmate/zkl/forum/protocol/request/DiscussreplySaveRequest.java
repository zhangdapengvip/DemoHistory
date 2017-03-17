package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求帖子回复
 */
public class DiscussreplySaveRequest extends DefaultRequest{

    private String pkDiscuss;//帖子主键
    private String pkUser;//回复人
    private String pkUserreply;//被回复人
    private String content;//内容

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPkDiscuss() {
        return pkDiscuss;
    }

    public void setPkDiscuss(String pkDiscuss) {
        this.pkDiscuss = pkDiscuss;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkUserreply() {
        return pkUserreply;
    }

    public void setPkUserreply(String pkUserreply) {
        this.pkUserreply = pkUserreply;
    }
}
