package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/21.
 * 我的求助
 */
public class HelpGetResponse extends DefaultResponse {
    public static final String LIST_WAIT = "0";
    public static final String LIST_HISTORY = "1";
    private List<Datas> list;

    public class Datas implements Serializable {
        private static final long serialVersionUID = -8249389815863863216L;
        private String pkUser;
        private String pkHelp;
        private String pkHospital;
        private String comment;
        private String state;//0等待，1是历史
        private String title;

        public String getPkUser() {
            return pkUser;
        }

        public void setPkUser(String pkUser) {
            this.pkUser = pkUser;
        }

        public String getPkHelp() {
            return pkHelp;
        }

        public void setPkHelp(String pkHelp) {
            this.pkHelp = pkHelp;
        }

        public String getPkHospital() {
            return pkHospital;
        }

        public void setPkHospital(String pkHospital) {
            this.pkHospital = pkHospital;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public List<Datas> getList() {
        return list;
    }

    public void setList(List<Datas> datas) {
        this.list = datas;
    }
}
