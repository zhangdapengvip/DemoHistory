package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 基因检测申请
 */
public class GeneapplySaveRequest extends DefaultRequest {

    private String age;//年龄
    private String name;//名称
    private String phone;//电话
    private String pkGenetest;//主键
    private String pkUser;//用户主键
    private String sex;//性别
    private String source = "1";//0医生1大众

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getPkGenetest() {
        return pkGenetest;
    }

    public void setPkGenetest(String pkGenetest) {
        this.pkGenetest = pkGenetest;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
