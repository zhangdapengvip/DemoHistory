package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应商品类型
 */
public class GoodsTypePageResponse extends DefaultResponse {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private String name;//名称
        private String selectedImage;
        private String pkGoodstype;//类型主键
        private String uncheckedImage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSelectedImage() {
            return selectedImage;
        }

        public void setSelectedImage(String selectedImage) {
            this.selectedImage = selectedImage;
        }

        public String getPkGoodstype() {
            return pkGoodstype;
        }

        public void setPkGoodstype(String pkGoodstype) {
            this.pkGoodstype = pkGoodstype;
        }

        public String getUncheckedImage() {
            return uncheckedImage;
        }

        public void setUncheckedImage(String uncheckedImage) {
            this.uncheckedImage = uncheckedImage;
        }
    }
}
