package com.yijia.patient.ui.bean;

import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ZeroAries on 2016/3/16.
 * 特需——寻求帮助——求助信息——条目信息{@link com.yijia.patient.ui.activity.PatientInformationActivity}
 */
public class InformationInfo {

    public int btnId;//按钮Id
    public int groupId;//图片ViewGroupId
    public View itemView;//按钮View
    public LinearLayout groupView;//ViewGroup
    public ArrayList<File> fileList = new ArrayList<>();//ViewGroup图片集合

}
