package com.yijia.patient.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/21.
 * 视野
 */
public class OnlineqaPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<Rows> rows;

        public class Rows implements Serializable {
            private static final long serialVersionUID = -6604605088646483977L;
            private String pkOnlineqa;//主键
            private String pkUser;//用户主键
            private String title;//标题
            private String begindate;//开始日期
            private String begintime;//开始时间
            private String endtime;//结束时间
            private String videourl;//视频
            private String name;//用户姓名
            private String image;//用户头像
            private String zhicheng;//用户职称
            private String comment;//名医介绍

            public String getPkOnlineqa() {
                return pkOnlineqa;
            }

            public void setPkOnlineqa(String pkOnlineqa) {
                this.pkOnlineqa = pkOnlineqa;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBegindate() {
                return begindate;
            }

            public void setBegindate(String begindate) {
                this.begindate = begindate;
            }

            public String getBegintime() {
                return begintime;
            }

            public void setBegintime(String begintime) {
                this.begintime = begintime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getVideourl() {
                return videourl;
            }

            public void setVideourl(String videourl) {
                this.videourl = videourl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getZhicheng() {
                return zhicheng;
            }

            public void setZhicheng(String zhicheng) {
                this.zhicheng = zhicheng;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
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
