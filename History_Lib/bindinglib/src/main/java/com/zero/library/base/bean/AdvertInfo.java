package com.zero.library.base.bean;

/**
 * @author zhangdapeng 2015-8-25 上午11:35:20 首页轮播广告
 */
public class AdvertInfo {
    private String date = "";
    private String title = "";// 详情标题
    private String picture = "";// 图片URL
    private String pageurl = ""; // 详情URL

    public AdvertInfo() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }
}
