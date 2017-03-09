package com.yijia.patient.ui.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 家庭疾病信息列表
 */
public class FamilydiseaseinfoListResponse extends DefaultResponse {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        private String pkFamilydiseaseinfo;//主键
        private String pkBasicinfo;//外键
        private String diseasename;//疾病名称
        private String member;//患病成员
        private String age;//患病年龄
        private String content;//简单说明

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pkFamilydiseaseinfo);
            dest.writeString(this.pkBasicinfo);
            dest.writeString(this.diseasename);
            dest.writeString(this.member);
            dest.writeString(this.age);
            dest.writeString(this.content);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.pkFamilydiseaseinfo = in.readString();
            this.pkBasicinfo = in.readString();
            this.diseasename = in.readString();
            this.member = in.readString();
            this.age = in.readString();
            this.content = in.readString();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
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
