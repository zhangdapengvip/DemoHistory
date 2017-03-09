package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

/**
 * Created by ZeroAries on 2016/3/18.
 * 登录返回
 */
public class LoginResponse extends DefaultResponse {
    public static final String TYPE_UNAUTH = "0";
    public static final String TYPE_AUTHING = "1";
    public static final String TYPE_AUTH = "2";

    private Datas datas = new Datas();

    public class Datas {
        private String pkUser;//主键
        private String phone;//手机号
        private String username;//帐号
        private String name;//姓名
        private String domain;//擅长领域 0肿瘤科1心血管科2其它科
        private String hospital;//医院
        private String department;//科室
        private String title;//职称
        private String review;//用户类型 0未认证 2已认证 1认证中
        private String usertype;//用户类型 0未认证 2已认证 1认证中
        private String image;//头像
        private String certificate;//执业医师证书
        private String registerdate;//注册日期
        private String lastlogindate;//最后登录日期
        private String comment;//名医介绍
        private String pk_other;//其他登录主键
        private String othertype;//登录类型

        public String getPkUser() {
            return pkUser;
        }

        public void setPkUser(String pkUser) {
            this.pkUser = pkUser;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getRegisterdate() {
            return registerdate;
        }

        public void setRegisterdate(String registerdate) {
            this.registerdate = registerdate;
        }

        public String getLastlogindate() {
            return lastlogindate;
        }

        public void setLastlogindate(String lastlogindate) {
            this.lastlogindate = lastlogindate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPk_other() {
            return pk_other;
        }

        public void setPk_other(String pk_other) {
            this.pk_other = pk_other;
        }

        public String getOthertype() {
            return othertype;
        }

        public void setOthertype(String othertype) {
            this.othertype = othertype;
        }
    }

    public Datas getDatas() {
        return datas;
    }

    public void setDatas(Datas datas) {
        this.datas = datas;
    }
}
