package com.yijia.patient.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 健康信息列表
 */
public class HealthyinfoListResponse extends DefaultResponse {

    private List<HealthyInfo> list;

    public List<HealthyInfo> getList() {
        return list;
    }

    public void setList(List<HealthyInfo> list) {
        this.list = list;
    }
}