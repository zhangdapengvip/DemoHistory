package com.yijia.patient.ui.bean;

import android.support.annotation.ArrayRes;

import com.yijia.patient.R;
import com.zero.library.base.bean.SpinnerItem;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ZeroAries on 2016/3/11.
 * 常量信息
 */
public class Constants {
    public static final int MAX_AGE = 150;
    public static final String UNCHECK = "0";
    public static final String CHECK = "1";
    public static final String SEX_MAN = "0";
    public static final String SEX_WOMAN = "1";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String LOGIN_ID = "login_id";
    public static final String PLATFORM = "platform";
    public static final long REFRESH_TIME = 1000 * 60 * 30;

    /**
     * @return 科室信息列表
     */
    public static final List<OfficeInfo> getOfficeItemList() {
        String[] idArr = UtilsUi.getStringArray(R.array.domain_key_list);
        String[] fullArr = UtilsUi.getStringArray(R.array.domain_fullname_list);
        String[] shortArr = UtilsUi.getStringArray(R.array.domain_shortname_list);
        ArrayList<OfficeInfo> officeInfos = new ArrayList<>();
        for (int item = 0; item < fullArr.length; item++) {
            OfficeInfo info = new OfficeInfo(idArr[item], fullArr[item], shortArr[item]);
            officeInfos.add(info);
        }
        return officeInfos;
    }

    /**
     * @return 擅长领域列表
     */
    public static final List<SpinnerItem> getDomainList() {
        return getKeyValue(R.array.domain_key_list, R.array.domain_fullname_list);
    }

    /**
     * @return 职称列表
     */
    public static final List<SpinnerItem> getTitleList() {
        Map<String, String> valueMap = getTitleMap();
        Set<Map.Entry<String, String>> entry = valueMap.entrySet();
        ArrayList<SpinnerItem> titleList = new ArrayList<>();
        for (Map.Entry<String, String> item : entry) {
            SpinnerItem info = new SpinnerItem(item.getKey(), item.getValue());
            titleList.add(info);
        }
        return titleList;
    }

    /**
     * @return 职称map
     */
    public static Map<String, String> getTitleMap() {
        Map<String, String> valueMap = new HashMap<>();
        String[] keyArr = UtilsUi.getStringArray(R.array.title_key_list);
        String[] valueArr = UtilsUi.getStringArray(R.array.title_item_list);
        for (int item = 0; item < valueArr.length; item++) {
            valueMap.put(keyArr[item], valueArr[item]);
        }
        return valueMap;
    }

    /**
     * @return 受试者类型列表
     */
    public static List<SpinnerItem> getRecruitType() {
        return getKeyValue(R.array.recruit_type_key, R.array.recruit_type);
    }

    /**
     * @return 性别列表
     */
    public static List<SpinnerItem> getRecruitSex() {
        return getKeyValue(R.array.recruit_sex_key, R.array.recruit_sex);
    }

    /**
     * @return 婚姻状况列表
     */
    public static List<SpinnerItem> getRecruitMarriage() {
        return getKeyValue(R.array.recruit_marriage_key, R.array.recruit_marriage);
    }

    /**
     * @return 实验分类列表
     */
    public static List<SpinnerItem> getRecruitTestType() {
        return getKeyValue(R.array.recruit_test_type_key, R.array.recruit_test_type);
    }


    private static List<SpinnerItem> getKeyValue(@ArrayRes int resKey, @ArrayRes int resValue) {
        String[] keyArr = UtilsUi.getStringArray(resKey);
        String[] valueArr = UtilsUi.getStringArray(resValue);
        ArrayList<SpinnerItem> itemList = new ArrayList<>();
        for (int item = 0; item < valueArr.length; item++) {
            SpinnerItem info = new SpinnerItem(keyArr[item], valueArr[item]);
            itemList.add(info);
        }
        return itemList;
    }
}
