package com.healthsoulmate.zkl.forum.bean;

/**
 * Created by ZeroAries on 2016/5/4.
 * 回复监听
 */
public class ReplyInfo {
    private String tag;
    private String replyName;
    private String pkReply;
    private String pkDiscuss;

    public ReplyInfo(String pkDiscuss, String pkReply, String replyName, String tag) {
        this.pkDiscuss = pkDiscuss;
        this.pkReply = pkReply;
        this.replyName = replyName;
        this.tag = tag;
    }

    public String getPkDiscuss() {
        return pkDiscuss;
    }

    public void setPkDiscuss(String pkDiscuss) {
        this.pkDiscuss = pkDiscuss;
    }

    public String getPkReply() {
        return pkReply;
    }

    public void setPkReply(String pkReply) {
        this.pkReply = pkReply;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
