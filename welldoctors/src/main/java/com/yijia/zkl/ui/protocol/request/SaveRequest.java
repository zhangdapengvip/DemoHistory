package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/21.
 * 发布招募
 */
public class SaveRequest extends DefaultRequest {

    private String marriageState;//婚姻状况0不限1未婚2已婚3离异
    private String startDate;//开始时间   格式2010-01-02
    private String drugIndication;//药物适应症
    private String designType;//设计类型
    private String textTarget;//试验目的
    private String textMethod;//测试方法
    private String textSex;//测试人性别0不限1男2女
    private String overDate;//结束时间   格式2010-01-02
    private String textNumber;//测试人数
    private String context;//入选标准
    private String textClassification;//试验分类  0I期1II期2III期 3IV期
    private String joinRequire;//排除标准
    private String textType;//测试人类型0患者1健康志愿者
    private String launchOrg;//发起组织
    private String launchName;//发起人姓名
    private String launchLink;//联系方式
    private String pkUser;//用户主键

    public String getMarriageState() {
        return marriageState;
    }

    public void setMarriageState(String marriageState) {
        this.marriageState = marriageState;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    public String getDrugIndication() {
        return drugIndication;
    }

    public void setDrugIndication(String drugIndication) {
        this.drugIndication = drugIndication;
    }

    public String getJoinRequire() {
        return joinRequire;
    }

    public void setJoinRequire(String joinRequire) {
        this.joinRequire = joinRequire;
    }

    public String getOverDate() {
        return overDate;
    }

    public void setOverDate(String overDate) {
        this.overDate = overDate;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTextClassification() {
        return textClassification;
    }

    public void setTextClassification(String textClassification) {
        this.textClassification = textClassification;
    }

    public String getTextMethod() {
        return textMethod;
    }

    public void setTextMethod(String textMethod) {
        this.textMethod = textMethod;
    }

    public String getTextNumber() {
        return textNumber;
    }

    public void setTextNumber(String textNumber) {
        this.textNumber = textNumber;
    }

    public String getTextSex() {
        return textSex;
    }

    public void setTextSex(String textSex) {
        this.textSex = textSex;
    }

    public String getTextTarget() {
        return textTarget;
    }

    public void setTextTarget(String textTarget) {
        this.textTarget = textTarget;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public String getLaunchLink() {
        return launchLink;
    }

    public void setLaunchLink(String launchLink) {
        this.launchLink = launchLink;
    }

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public String getLaunchOrg() {
        return launchOrg;
    }

    public void setLaunchOrg(String launchOrg) {
        this.launchOrg = launchOrg;
    }
}
