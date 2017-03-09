package com.healthsoulmate.zkl.ui.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZeroAries on 2016/5/19.
 * 新闻信息
 */
public class NewsBean implements Parcelable {
    private String title;//标题
    private String infourl;//资讯内容
    private String pkInformation;//主键
    private String about;//简介
    private String releasedate;//发布日期
    private String pictureurl;//图片
    private String iscarousel;//是否轮播
    private String readnum;//阅读数量
    private String pkUser;//发布人

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfourl() {
        return infourl;
    }

    public void setInfourl(String infourl) {
        this.infourl = infourl;
    }

    public String getPkInformation() {
        return pkInformation;
    }

    public void setPkInformation(String pkInformation) {
        this.pkInformation = pkInformation;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getIscarousel() {
        return iscarousel;
    }

    public void setIscarousel(String iscarousel) {
        this.iscarousel = iscarousel;
    }

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.infourl);
        dest.writeString(this.pkInformation);
        dest.writeString(this.about);
        dest.writeString(this.releasedate);
        dest.writeString(this.pictureurl);
        dest.writeString(this.iscarousel);
        dest.writeString(this.readnum);
        dest.writeString(this.pkUser);
    }

    public NewsBean() {
    }

    protected NewsBean(Parcel in) {
        this.title = in.readString();
        this.infourl = in.readString();
        this.pkInformation = in.readString();
        this.about = in.readString();
        this.releasedate = in.readString();
        this.pictureurl = in.readString();
        this.iscarousel = in.readString();
        this.readnum = in.readString();
        this.pkUser = in.readString();
    }

    public static final Parcelable.Creator<NewsBean> CREATOR = new Parcelable.Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel source) {
            return new NewsBean(source);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };
}