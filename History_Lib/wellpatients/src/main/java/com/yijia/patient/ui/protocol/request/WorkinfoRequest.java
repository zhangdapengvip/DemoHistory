package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 工作信息保存
 */
public class WorkinfoRequest extends DefaultRequest {
    private String pkWorkinfo;//主键
    private String pkBasicinfo;//外键
    private String occupation;//职业
    private String post;//职务
    private String workinghours;//工作时间起始
    private String workinghours1;//工作时间停止
    private String iswork;//是否加班
    private String frequency;//加班频率
    private String overtime;//加班时间
    private String description;//工作压力描述
    private String otherdescription;//其他描述

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getIswork() {
        return iswork;
    }

    public void setIswork(String iswork) {
        this.iswork = iswork;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOtherdescription() {
        return otherdescription;
    }

    public void setOtherdescription(String otherdescription) {
        this.otherdescription = otherdescription;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getPkBasicinfo() {
        return pkBasicinfo;
    }

    public void setPkBasicinfo(String pkBasicinfo) {
        this.pkBasicinfo = pkBasicinfo;
    }

    public String getPkWorkinfo() {
        return pkWorkinfo;
    }

    public void setPkWorkinfo(String pkWorkinfo) {
        this.pkWorkinfo = pkWorkinfo;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWorkinghours() {
        return workinghours;
    }

    public void setWorkinghours(String workinghours) {
        this.workinghours = workinghours;
    }

    public String getWorkinghours1() {
        return workinghours1;
    }

    public void setWorkinghours1(String workinghours1) {
        this.workinghours1 = workinghours1;
    }
}
