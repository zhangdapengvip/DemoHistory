package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 医疗援助列表
 */
public class HospitalPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<Rows> rows;

        public class Rows implements Serializable {
            private static final long serialVersionUID = -8360046367638659012L;
            private String detail;//记录链接页面内容了
            private String pkHospital;//机构主键
            private String about;//简介
            private String pictureurl;//机构图片
            private String aboutPageUrl;//简介页面链接
            private String hospitalname;//机构名称

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getPkHospital() {
                return pkHospital;
            }

            public void setPkHospital(String pkHospital) {
                this.pkHospital = pkHospital;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getPictureurl() {
                return pictureurl;
            }

            public void setPictureurl(String pictureurl) {
                this.pictureurl = pictureurl;
            }

            public String getHospitalname() {
                return hospitalname;
            }

            public void setHospitalname(String hospitalname) {
                this.hospitalname = hospitalname;
            }

            public String getAboutPageUrl() {
                return aboutPageUrl;
            }

            public void setAboutPageUrl(String aboutPageUrl) {
                this.aboutPageUrl = aboutPageUrl;
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
