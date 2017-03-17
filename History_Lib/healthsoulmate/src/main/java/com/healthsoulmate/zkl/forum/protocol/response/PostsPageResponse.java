package com.healthsoulmate.zkl.forum.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应帖子列表
 */
public class PostsPageResponse extends DefaultResponse {

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        private int total;
        private boolean lastPage;
        private List<RowsBean> rows;

        public static class RowsBean implements Parcelable {

            private String isup;//是否置顶
            private String pkDiscuzsection;//版块
            private String posttime;//发布时间
            private String replyNum;//回复量
            private String content;//内容
            private String postsTitle;//标题
            private String isgood;//是否加精
            private String readNum;//阅读量
            private String userName;//用户名称
            private String images;//帖子图片  数组
            private String isdiscuss;//能否跟帖
            private String pkPosts;//主键
            private String pkUser;//作者
            private String userImage;//作者头像
            private String sectionname;//论坛板块名称
            private String isSelfPosts;
            private String deleteflag;
            private String pkPostfavorites;
            private String commenttime;//最后评论时间

            public String getIsup() {
                return isup;
            }

            public void setIsup(String isup) {
                this.isup = isup;
            }

            public String getPkDiscuzsection() {
                return pkDiscuzsection;
            }

            public void setPkDiscuzsection(String pkDiscuzsection) {
                this.pkDiscuzsection = pkDiscuzsection;
            }

            public String getPosttime() {
                return posttime;
            }

            public void setPosttime(String posttime) {
                this.posttime = posttime;
            }

            public String getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(String replyNum) {
                this.replyNum = replyNum;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPostsTitle() {
                return postsTitle;
            }

            public void setPostsTitle(String title) {
                this.postsTitle = title;
            }

            public String getIsgood() {
                return isgood;
            }

            public void setIsgood(String isgood) {
                this.isgood = isgood;
            }

            public String getReadNum() {
                return readNum;
            }

            public void setReadNum(String readNum) {
                this.readNum = readNum;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getIsdiscuss() {
                return isdiscuss;
            }

            public void setIsdiscuss(String isdiscuss) {
                this.isdiscuss = isdiscuss;
            }

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

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }

            public String getDeleteflag() {
                return deleteflag;
            }

            public void setDeleteflag(String deleteflag) {
                this.deleteflag = deleteflag;
            }

            public String getIsSelfPosts() {
                return isSelfPosts;
            }

            public void setIsSelfPosts(String isSelfPosts) {
                this.isSelfPosts = isSelfPosts;
            }

            public String getSectionname() {
                return sectionname;
            }

            public void setSectionname(String sectionname) {
                this.sectionname = sectionname;
            }

            public String getPkPostfavorites() {
                return pkPostfavorites;
            }

            public void setPkPostfavorites(String pkPostfavorites) {
                this.pkPostfavorites = pkPostfavorites;
            }

            public String getCommenttime() {
                return commenttime;
            }

            public void setCommenttime(String commenttime) {
                this.commenttime = commenttime;
            }

            public RowsBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.isup);
                dest.writeString(this.pkDiscuzsection);
                dest.writeString(this.posttime);
                dest.writeString(this.replyNum);
                dest.writeString(this.content);
                dest.writeString(this.postsTitle);
                dest.writeString(this.isgood);
                dest.writeString(this.readNum);
                dest.writeString(this.userName);
                dest.writeString(this.images);
                dest.writeString(this.isdiscuss);
                dest.writeString(this.pkPosts);
                dest.writeString(this.pkUser);
                dest.writeString(this.userImage);
                dest.writeString(this.sectionname);
                dest.writeString(this.isSelfPosts);
                dest.writeString(this.deleteflag);
                dest.writeString(this.pkPostfavorites);
                dest.writeString(this.commenttime);
            }

            protected RowsBean(Parcel in) {
                this.isup = in.readString();
                this.pkDiscuzsection = in.readString();
                this.posttime = in.readString();
                this.replyNum = in.readString();
                this.content = in.readString();
                this.postsTitle = in.readString();
                this.isgood = in.readString();
                this.readNum = in.readString();
                this.userName = in.readString();
                this.images = in.readString();
                this.isdiscuss = in.readString();
                this.pkPosts = in.readString();
                this.pkUser = in.readString();
                this.userImage = in.readString();
                this.sectionname = in.readString();
                this.isSelfPosts = in.readString();
                this.deleteflag = in.readString();
                this.pkPostfavorites = in.readString();
                this.commenttime = in.readString();
            }

            public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
                @Override
                public RowsBean createFromParcel(Parcel source) {
                    return new RowsBean(source);
                }

                @Override
                public RowsBean[] newArray(int size) {
                    return new RowsBean[size];
                }
            };
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }
    }
}
