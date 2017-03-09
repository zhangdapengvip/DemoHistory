package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 基因检测推荐历史
 */
public class GeneapplyPageResponse extends DefaultResponse {

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        private List<RowsBean> rows;

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private String age;
            private String name;
            private String phone;
            private String pkGenetest;
            private String pkUser;
            private String sex;

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPkGenetest() {
                return pkGenetest;
            }

            public void setPkGenetest(String pkGenetest) {
                this.pkGenetest = pkGenetest;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }
    }
}
