package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/21.
 * 求助详情
 */
public class HelpDetailRsponse extends DefaultResponse {
    private List<Datas> list;

    public class Datas {
        private int picturetype;
        private String pictureurl;
        private String pkHelp;
        private String pkHelpdetail;

        public int getPicturetype() {
            return picturetype;
        }

        public void setPicturetype(int picturetype) {
            this.picturetype = picturetype;
        }

        public String getPictureurl() {
            return pictureurl;
        }

        public void setPictureurl(String pictureurl) {
            this.pictureurl = pictureurl;
        }

        public String getPkHelp() {
            return pkHelp;
        }

        public void setPkHelp(String pkHelp) {
            this.pkHelp = pkHelp;
        }

        public String getPkHelpdetail() {
            return pkHelpdetail;
        }

        public void setPkHelpdetail(String pkHelpdetail) {
            this.pkHelpdetail = pkHelpdetail;
        }
    }

    public List<Datas> getList() {
        return list;
    }

    public void setList(List<Datas> list) {
        this.list = list;
    }
}
