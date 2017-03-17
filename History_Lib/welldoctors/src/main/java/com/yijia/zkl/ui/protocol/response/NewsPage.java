package com.yijia.zkl.ui.protocol.response;

import java.io.Serializable;

/**
 * Created by ZeroAries on 2016/5/9.
 * 新闻内容
 */
public class NewsPage implements Serializable {
    private static final long serialVersionUID = -5373259810272945993L;
    private String pkInformation;//主键
    private String infourl;//资讯内容
    private String title;//标题
    private String pictureurl;//图片
    private String domain;//领域
    private String type;//类型
    private String releasedate;//发布日期
    private String readnum;//阅读数量
    private String iscarousel;//是否轮播

    public String getPkInformation() {
        return pkInformation;
    }

    public void setPkInformation(String pkInformation) {
        this.pkInformation = pkInformation;
    }

    public String getInfourl() {
        return infourl;
    }

    public void setInfourl(String infourl) {
        this.infourl = infourl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }

    public String getIscarousel() {
        return iscarousel;
    }

    public void setIscarousel(String iscarousel) {
        this.iscarousel = iscarousel;
    }
}
