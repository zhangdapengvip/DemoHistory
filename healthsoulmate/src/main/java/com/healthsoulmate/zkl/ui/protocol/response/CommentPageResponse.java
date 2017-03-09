package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应评价列表
 */
public class CommentPageResponse extends DefaultResponse {

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
            private String content;//评论内容
            private String anonymous;//是否匿名
            private String pkGoodsorder;//订单商品
            private String userImage;//发送人头像
            private int score;//评分
            private String replytime;//回复时间
            private String userName;//发送人姓名
            private String reply;//回复
            private String commenttime;//评论时间
            private String deleteflag;//删除标志
            private String pkUser;//评论人主键
            private String pkComment;//主键

            public String getAnonymous() {
                return anonymous;
            }

            public void setAnonymous(String anonymous) {
                this.anonymous = anonymous;
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

            public String getPkComment() {
                return pkComment;
            }

            public void setPkComment(String pkComment) {
                this.pkComment = pkComment;
            }

            public String getPkGoodsorder() {
                return pkGoodsorder;
            }

            public void setPkGoodsorder(String pkGoodsorder) {
                this.pkGoodsorder = pkGoodsorder;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public String getReplytime() {
                return replytime;
            }

            public void setReplytime(String replytime) {
                this.replytime = replytime;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
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
