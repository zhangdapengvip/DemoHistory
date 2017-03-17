package com.healthsoulmate.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

/**
 * Created by ZeroAries on 2016/3/18.
 * 登录返回
 */
public class LoginResponse extends DefaultResponse {

    private DatasBean datas = new DatasBean();

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {

        private String pkUser;//主键
        private String uuid;//uuid
        private String uuidtype;//uuid类型
        private String sex;
        private String birthday;//出生日期
        private String phone;//手机号
        private String registertime;//注册时间
        private String image;//头像
        private String usertype;//用户类型
        private String username;//用户名
        private String lastlogintime;//最后登录时间
        private String name;//昵称
        private String special;//是否特别用户
        private String comment;//个人介绍
        private String hospital;//医院
        private String department;//科室
        private String title;//职称
        private String certificate;//执业医师证书
        private String isFollow;//是否关注

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLastlogintime() {
            return lastlogintime;
        }

        public void setLastlogintime(String lastlogintime) {
            this.lastlogintime = lastlogintime;
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

        public String getPkUser() {
            return pkUser;
        }

        public void setPkUser(String pkUser) {
            this.pkUser = pkUser;
        }

        public String getRegistertime() {
            return registertime;
        }

        public void setRegistertime(String registertime) {
            this.registertime = registertime;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUuidtype() {
            return uuidtype;
        }

        public void setUuidtype(String uuidtype) {
            this.uuidtype = uuidtype;
        }

        public String getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(String isFollow) {
            this.isFollow = isFollow;
        }
    }
}
