package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 家庭疾病史
 */
public class FamilyDiseaseInfoRequest extends DefaultRequest {
    private String pkFamilydiseaseinfo;//主键
    private String pkBasicinfo;//外键
    private String diseasename;//疾病名称
    private String member;//患病成员
    private String age;//患病年龄
    private String content;//简单说明

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDiseasename() {
        return diseasename;
    }

    public void setDiseasename(String diseasename) {
        this.diseasename = diseasename;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getPkBasicinfo() {
        return pkBasicinfo;
    }

    public void setPkBasicinfo(String pkBasicinfo) {
        this.pkBasicinfo = pkBasicinfo;
    }

    public String getPkFamilydiseaseinfo() {
        return pkFamilydiseaseinfo;
    }

    public void setPkFamilydiseaseinfo(String pkFamilydiseaseinfo) {
        this.pkFamilydiseaseinfo = pkFamilydiseaseinfo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
