package com.healthsoulmate.zkl.forum.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应收藏板块
 */
public class FavoriteResponse extends DefaultResponse {

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


        public static class RowsBean {
            private String canceltime;//取消收藏时间
            private String discuzsectionName;//板块名称
            private String pkDiscuzsection;//板块主键
            private String favoritestime;//收藏时间
            private String pkSectionfavorites;//主键
            private int deleteflag;//删除标志
            private String pkUser;//用户主键

            public String getCanceltime() {
                return canceltime;
            }

            public void setCanceltime(String canceltime) {
                this.canceltime = canceltime;
            }

            public String getDiscuzsectionName() {
                return discuzsectionName;
            }

            public void setDiscuzsectionName(String discuzsectionName) {
                this.discuzsectionName = discuzsectionName;
            }

            public String getPkDiscuzsection() {
                return pkDiscuzsection;
            }

            public void setPkDiscuzsection(String pkDiscuzsection) {
                this.pkDiscuzsection = pkDiscuzsection;
            }

            public String getFavoritestime() {
                return favoritestime;
            }

            public void setFavoritestime(String favoritestime) {
                this.favoritestime = favoritestime;
            }

            public String getPkSectionfavorites() {
                return pkSectionfavorites;
            }

            public void setPkSectionfavorites(String pkSectionfavorites) {
                this.pkSectionfavorites = pkSectionfavorites;
            }

            public int getDeleteflag() {
                return deleteflag;
            }

            public void setDeleteflag(int deleteflag) {
                this.deleteflag = deleteflag;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }
        }

        private List<RowsBean> rows;

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
