package com.healthsoulmate.zkl.forum.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 请求举报
 */
public class PostreportSaveRequest extends DefaultRequest {

    private String pkPosts;//帖子主键
    private String pkUser;//举报人
    private String pkUserpassive;//被举报人
    private String reportreason;//举报原因  类型
    private String reportcontent;//举报内容

    public String getPkPosts() {
        return pkPosts;
    }

    public void setPkPosts(String pkPosts) {
        this.pkPosts = pkPosts;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkUserpassive() {
        return pkUserpassive;
    }

    public void setPkUserpassive(String pkUserpassive) {
        this.pkUserpassive = pkUserpassive;
    }

    public String getReportreason() {
        return reportreason;
    }

    public void setReportreason(String reportreason) {
        this.reportreason = reportreason;
    }

    public String getReportcontent() {
        return reportcontent;
    }

    public void setReportcontent(String reportcontent) {
        this.reportcontent = reportcontent;
    }
}
