package com.yijia.patient.ui.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 工作信息列表
 */
public class WorkinfoListResponse extends DefaultResponse {

    private List<ListBean> list = new ArrayList<>();

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
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

        public ListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pkWorkinfo);
            dest.writeString(this.pkBasicinfo);
            dest.writeString(this.occupation);
            dest.writeString(this.post);
            dest.writeString(this.workinghours);
            dest.writeString(this.workinghours1);
            dest.writeString(this.iswork);
            dest.writeString(this.frequency);
            dest.writeString(this.overtime);
            dest.writeString(this.description);
            dest.writeString(this.otherdescription);
        }

        protected ListBean(Parcel in) {
            this.pkWorkinfo = in.readString();
            this.pkBasicinfo = in.readString();
            this.occupation = in.readString();
            this.post = in.readString();
            this.workinghours = in.readString();
            this.workinghours1 = in.readString();
            this.iswork = in.readString();
            this.frequency = in.readString();
            this.overtime = in.readString();
            this.description = in.readString();
            this.otherdescription = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }
}
