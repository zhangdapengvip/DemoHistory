package com.healthsoulmate.zkl.ui.bean;

import android.support.annotation.ArrayRes;

import com.zero.library.base.bean.SpinnerItem;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/11.
 * 常量信息
 */
public class Constants {
    public static final int MAX_AGE = 150;
    public static final String SEX_MAN = "1";
    public static final String SEX_WOMAN = "2";
    public static final String SHOPPING_COUNT = "shopping_count";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String LOGIN_ID = "login_id";
    public static final String PLATFORM = "platform";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final long REFRESH_TIME = 1000 * 60 * 30;


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
