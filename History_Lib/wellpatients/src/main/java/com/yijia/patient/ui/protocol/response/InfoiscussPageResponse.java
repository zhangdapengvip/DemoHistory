package com.yijia.patient.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 拷贝用
 */
public class InfoiscussPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<Rows> rows;

        public class Rows {
            private String content;//评论内容
            private String pkInfomation;//资讯主键
            private String userImage;//用户头像
            private String discusstime;//评论时间
            private String userName;//用户姓名

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPkInfomation() {
                return pkInfomation;
            }

            public void setPkInfomation(String pkInfomation) {
                this.pkInfomation = pkInfomation;
            }

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }

            public String getDiscusstime() {
                return discusstime;
            }

            public void setDiscusstime(String discusstime) {
                this.discusstime = discusstime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public List<Rows> getRows() {
            return rows;
        }

        public void setRows(List<Rows> rows) {
            this.rows = rows;
        }
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
