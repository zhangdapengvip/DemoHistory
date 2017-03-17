package com.healthsoulmate.zkl.forum.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应关注列表
 */
public class UserfocusPageResponse extends DefaultResponse {

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
            private String canceltime;
            private String pkUserbefocus;//被关注主键
            private String focustime;//收藏时间
            private String userImage;//关注人头像
            private String userbefocusImage;//被关注人头像
            private String userName;//关注人姓名
            private String pkUserfocus;//关注主键
            private String userbefocusName;//被关注人姓名
            private String deleteflag;
            private String pkUser;//关注人主键

            public String getCanceltime() {
                return canceltime;
            }

            public void setCanceltime(String canceltime) {
                this.canceltime = canceltime;
            }

            public String getPkUserbefocus() {
                return pkUserbefocus;
            }

            public void setPkUserbefocus(String pkUserbefocus) {
                this.pkUserbefocus = pkUserbefocus;
            }

            public String getFocustime() {
                return focustime;
            }

            public void setFocustime(String focustime) {
                this.focustime = focustime;
            }

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }

            public String getUserbefocusImage() {
                return userbefocusImage;
            }

            public void setUserbefocusImage(String userbefocusImage) {
                this.userbefocusImage = userbefocusImage;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPkUserfocus() {
                return pkUserfocus;
            }

            public void setPkUserfocus(String pkUserfocus) {
                this.pkUserfocus = pkUserfocus;
            }

            public String getUserbefocusName() {
                return userbefocusName;
            }

            public void setUserbefocusName(String userbefocusName) {
                this.userbefocusName = userbefocusName;
            }

            public String getDeleteflag() {
                return deleteflag;
            }

            public void setDeleteflag(String deleteflag) {
                this.deleteflag = deleteflag;
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
