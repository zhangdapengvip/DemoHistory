package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 基本信息保存
 */
public class BasicinfoRequest extends DefaultRequest {
    private String pkUser;//用户主键
    private String pkBasicinfo;//主键
    private String name;//姓名
    private String sex;//性别
    private String birthday;//出生日期
    private String originplace;//籍贯
    private String nation;//民族
    private String birthplace;//出生地
    private String phone;//联系电话
    private String email;//电子邮箱
    private String height;//身高（cm）
    private String weight;//体重(kg）
    private String marital;//婚姻状况
    private String pregnant;//怀孕次
    private String abortion;//流产次
    private String childbirth;//生娩次
    private String livebirth;//活产次
    private String menstruation;//首次来月经的年龄是岁

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getAbortion() {
        return abortion;
    }

    public void setAbortion(String abortion) {
        this.abortion = abortion;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getChildbirth() {
        return childbirth;
    }

    public void setChildbirth(String childbirth) {
        this.childbirth = childbirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLivebirth() {
        return livebirth;
    }

    public void setLivebirth(String livebirth) {
        this.livebirth = livebirth;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(String menstruation) {
        this.menstruation = menstruation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOriginplace() {
        return originplace;
    }

    public void setOriginplace(String originplace) {
        this.originplace = originplace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPkBasicinfo() {
        return pkBasicinfo;
    }

    public void setPkBasicinfo(String pkBasicinfo) {
        this.pkBasicinfo = pkBasicinfo;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
