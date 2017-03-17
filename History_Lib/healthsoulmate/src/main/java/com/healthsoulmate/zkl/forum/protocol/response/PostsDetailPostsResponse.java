package com.healthsoulmate.zkl.forum.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应帖子详情
 */
public class PostsDetailPostsResponse extends DefaultResponse {

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        private boolean lastPage;
        private int total;

        private List<RowsBean> rows;


        public static class RowsBean implements Parcelable {
            private String commenttime;//评论时间
            private String content;//内容
            private String deleteflag;//删除标记
            private String images;//帖子图片(按顺序排列的)
            private String parentContent;//主楼(废弃,已由上级接口带入)
            private String pkDiscuss;//评论主键(回帖)
            private String pkPosts;//帖子主键
            private String pkUser;//评论人
            private String userName;//评论人姓名
            private String userImage;//评论人头像

            private List<DiscussreplysBean> discussreplys;//楼中楼(应该不超过两条)

            public static class DiscussreplysBean {
                private String pkDiscussreply;//楼中楼主键
                private String pkDiscuss;//评论主键(跟帖)
                private String pkUser;//评论人
                private String userName;//评论人名称
                private String pkUserreply;//回复人
                private String userReplyName;//回复人名称
                private String replytime;//回复日期
                private String content;//内容

                public String getPkDiscussreply() {
                    return pkDiscussreply;
                }

                public void setPkDiscussreply(String pkDiscussreply) {
                    this.pkDiscussreply = pkDiscussreply;
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

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getPkUserreply() {
                    return pkUserreply;
                }

                public void setPkUserreply(String pkUserreply) {
                    this.pkUserreply = pkUserreply;
                }

                public String getUserReplyName() {
                    return userReplyName;
                }

                public void setUserReplyName(String userReplyName) {
                    this.userReplyName = userReplyName;
                }

                public String getReplytime() {
                    return replytime;
                }

                public void setReplytime(String replytime) {
                    this.replytime = replytime;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public String getCommenttime() {
                return commenttime;
            }

            public void setCommenttime(String commenttime) {
                this.commenttime = commenttime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDeleteflag() {
                return deleteflag;
            }

            public void setDeleteflag(String deleteflag) {
                this.deleteflag = deleteflag;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getParentContent() {
                return parentContent;
            }

            public void setParentContent(String parentContent) {
                this.parentContent = parentContent;
            }

            public String getPkDiscuss() {
                return pkDiscuss;
            }

            public void setPkDiscuss(String pkDiscuss) {
                this.pkDiscuss = pkDiscuss;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }

            public List<DiscussreplysBean> getDiscussreplys() {
                return discussreplys;
            }

            public void setDiscussreplys(List<DiscussreplysBean> discussreplys) {
                this.discussreplys = discussreplys;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.commenttime);
                dest.writeString(this.content);
                dest.writeString(this.deleteflag);
                dest.writeString(this.images);
                dest.writeString(this.parentContent);
                dest.writeString(this.pkDiscuss);
                dest.writeString(this.pkPosts);
                dest.writeString(this.pkUser);
                dest.writeString(this.userName);
                dest.writeString(this.userImage);
            }

            public RowsBean() {
            }

            protected RowsBean(Parcel in) {
                this.commenttime = in.readString();
                this.content = in.readString();
                this.deleteflag = in.readString();
                this.images = in.readString();
                this.parentContent = in.readString();
                this.pkDiscuss = in.readString();
                this.pkPosts = in.readString();
                this.pkUser = in.readString();
                this.userName = in.readString();
                this.userImage = in.readString();
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

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }
    }
}
