package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/21.
 * 视野返回
 */
public class ViewPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<Rows> rows;

        public class Rows implements Serializable{
            private static final long serialVersionUID = -5174411928365187472L;
            private String about;//简介
            private String domain;//领域
            private String image;//新闻图片
            private String name;//用户姓名
            private String pkUser;//用户主键
            private String pkView;//主键
            private String releasedate;//发布日期
            private String title;//标题
            private String type;//类型
            private String videourl;//视频

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }

            public String getPkView() {
                return pkView;
            }

            public void setPkView(String pkView) {
                this.pkView = pkView;
            }

            public String getReleasedate() {
                return releasedate;
            }

            public void setReleasedate(String releasedate) {
                this.releasedate = releasedate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVideourl() {
                return videourl;
            }

            public void setVideourl(String videourl) {
                this.videourl = videourl;
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
