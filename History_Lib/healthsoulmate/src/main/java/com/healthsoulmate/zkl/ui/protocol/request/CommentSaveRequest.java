package com.healthsoulmate.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求保存评价
 */
public class CommentSaveRequest extends DefaultRequest {
    public static final String IS_ANONYMOUS = "0";//匿名
    public static final String UN_ANONYMOUS = "1";//非匿名

    private String pkOrdergoods;// 订单商品
    private String pkUser;// 评论人主键
    private String anonymous;// 是否匿名
    private String content;// 评论内容
    private String score;// 评分

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPkOrdergoods() {
        return pkOrdergoods;
    }

    public void setPkOrdergoods(String pkOrdergoods) {
        this.pkOrdergoods = pkOrdergoods;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
