package com.healthsoulmate.zkl.forum.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应拷贝用
 */
public class PageDiscussReplyResponse extends DefaultResponse {

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


        public static class RowsBean {
            private String pkDiscuss;//帖子评论主键
            private String content;//内容
            private String pkDiscussreply;//回复主键
            private String userName;//回复人名称
            private String userReplyName;//被回复人名称
            private String replyTime;//回复时间
            private String pkUserreply;//被回复人主键
            private String pkUser;//回复人主键

            public String getPkDiscuss() {
                return pkDiscuss;
            }

            public void setPkDiscuss(String pkDiscuss) {
                this.pkDiscuss = pkDiscuss;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPkDiscussreply() {
                return pkDiscussreply;
            }

            public void setPkDiscussreply(String pkDiscussreply) {
                this.pkDiscussreply = pkDiscussreply;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserReplyName() {
                return userReplyName;
            }

            public void setUserReplyName(String userReplyName) {
                this.userReplyName = userReplyName;
            }

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }

            public String getPkUserreply() {
                return pkUserreply;
            }

            public void setPkUserreply(String pkUserreply) {
                this.pkUserreply = pkUserreply;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }
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
