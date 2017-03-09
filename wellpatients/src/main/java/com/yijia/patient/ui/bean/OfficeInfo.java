package com.yijia.patient.ui.bean;

/**
 * Created by ZeroAries on 2016/3/15.
 * 科室选择信息
 */
public class OfficeInfo {
    public static final String TYPE_NEWS = "type_news";
    public static final String TYPE_VIEW = "type_view";
    public String id;
    public String fullName;
    public String shortName;
    private String viewType;

    public OfficeInfo(String id, String fullName, String shortName) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public String getId() {
        return id;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
}
