package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 基因检测
 */
public class GenetestPageResponse extends DefaultResponse {

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

        public static class RowsBean implements Serializable {
            private static final long serialVersionUID = -7187387102291359423L;
            private String contenturl;//内容链接
            private String contextInfo;//内容体
            private String imageurl;//图片链接
            private String pkGenetest;//主键
            private String startdate;//上线日期
            private String title;//标题

            public String getContenturl() {
                return contenturl;
            }

            public void setContenturl(String contenturl) {
                this.contenturl = contenturl;
            }

            public String getContextInfo() {
                return contextInfo;
            }

            public void setContextInfo(String contextInfo) {
                this.contextInfo = contextInfo;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public String getPkGenetest() {
                return pkGenetest;
            }

            public void setPkGenetest(String pkGenetest) {
                this.pkGenetest = pkGenetest;
            }

            public String getStartdate() {
                return startdate;
            }

            public void setStartdate(String startdate) {
                this.startdate = startdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
