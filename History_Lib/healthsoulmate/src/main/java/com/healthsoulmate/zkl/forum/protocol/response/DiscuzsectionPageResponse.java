package com.healthsoulmate.zkl.forum.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 论坛列表
 */
public class DiscuzsectionPageResponse extends DefaultResponse {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        private String isopen;//是否开启
        private String pkDiscuzsection;//主键
        private String pkSectionfavorites;//收藏主键
        private String postsNum;//帖子数量
        private String isrecommend;//是否推荐
        private String isFav;//是否收藏
        private String about;//介绍
        private String sectionname;//名称
        private String createtime;//创建时间
        private String ispost;//是否可发帖
        private String subPostsNum;//回帖数量
        private String pkParent;//上级主键
        private String imageurl;//图片

        public String getIsopen() {
            return isopen;
        }

        public void setIsopen(String isopen) {
            this.isopen = isopen;
        }

        public String getPkDiscuzsection() {
            return pkDiscuzsection;
        }

        public void setPkDiscuzsection(String pkDiscuzsection) {
            this.pkDiscuzsection = pkDiscuzsection;
        }

        public String getPkSectionfavorites() {
            return pkSectionfavorites;
        }

        public void setPkSectionfavorites(String pkSectionfavorites) {
            this.pkSectionfavorites = pkSectionfavorites;
        }

        public String getPostsNum() {
            return postsNum;
        }

        public void setPostsNum(String postsNum) {
            this.postsNum = postsNum;
        }

        public String getIsrecommend() {
            return isrecommend;
        }

        public void setIsrecommend(String isrecommend) {
            this.isrecommend = isrecommend;
        }

        public String getIsFav() {
            return isFav;
        }

        public void setIsFav(String isFav) {
            this.isFav = isFav;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getSectionname() {
            return sectionname;
        }

        public void setSectionname(String sectionname) {
            this.sectionname = sectionname;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getIspost() {
            return ispost;
        }

        public void setIspost(String ispost) {
            this.ispost = ispost;
        }

        public String getSubPostsNum() {
            return subPostsNum;
        }

        public void setSubPostsNum(String subPostsNum) {
            this.subPostsNum = subPostsNum;
        }

        public String getPkParent() {
            return pkParent;
        }

        public void setPkParent(String pkParent) {
            this.pkParent = pkParent;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.isopen);
            dest.writeString(this.pkDiscuzsection);
            dest.writeString(this.pkSectionfavorites);
            dest.writeString(this.postsNum);
            dest.writeString(this.isrecommend);
            dest.writeString(this.isFav);
            dest.writeString(this.about);
            dest.writeString(this.sectionname);
            dest.writeString(this.createtime);
            dest.writeString(this.ispost);
            dest.writeString(this.subPostsNum);
            dest.writeString(this.pkParent);
            dest.writeString(this.imageurl);
        }

        protected ListBean(Parcel in) {
            this.isopen = in.readString();
            this.pkDiscuzsection = in.readString();
            this.pkSectionfavorites = in.readString();
            this.postsNum = in.readString();
            this.isrecommend = in.readString();
            this.isFav = in.readString();
            this.about = in.readString();
            this.sectionname = in.readString();
            this.createtime = in.readString();
            this.ispost = in.readString();
            this.subPostsNum = in.readString();
            this.pkParent = in.readString();
            this.imageurl = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }
}
