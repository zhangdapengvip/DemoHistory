package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应评价计数接口
 */
public class CommentCommentnumResponse extends DefaultResponse {

    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private String commentnum;
        private String cpnum;
        private String hpnum;
        private String zpnum;

        public String getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(String commentnum) {
            this.commentnum = commentnum;
        }

        public String getCpnum() {
            return cpnum;
        }

        public void setCpnum(String cpnum) {
            this.cpnum = cpnum;
        }

        public String getHpnum() {
            return hpnum;
        }

        public void setHpnum(String hpnum) {
            this.hpnum = hpnum;
        }

        public String getZpnum() {
            return zpnum;
        }

        public void setZpnum(String zpnum) {
            this.zpnum = zpnum;
        }
    }
}
