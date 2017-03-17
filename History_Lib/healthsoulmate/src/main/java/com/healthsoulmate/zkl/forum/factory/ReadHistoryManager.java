package com.healthsoulmate.zkl.forum.factory;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/5/27.
 * 阅读历史
 */
public class ReadHistoryManager {

    private final static String SAVE_TAG = ReadHistoryManager.class.getSimpleName();
    private final static int MAX_COUNT = 20;

    private volatile static List<PostsPageResponse.PageBean.RowsBean> readList = new ArrayList<>();

    public static void add(PostsPageResponse.PageBean.RowsBean item) {
        initFromLocal();
        List<PostsPageResponse.PageBean.RowsBean> newReadList = new ArrayList<>();
        newReadList.add(item);
        for (PostsPageResponse.PageBean.RowsBean itemInfo : readList) {
            if (itemInfo.getPkPosts().equals(item.getPkPosts())) continue;
            newReadList.add(itemInfo);
        }
        if (readList.size() > MAX_COUNT) {
            readList.remove(MAX_COUNT);
        }
        saveToLocal(newReadList);
    }

    public static List<PostsPageResponse.PageBean.RowsBean> getLoacl() {
        initFromLocal();
        return readList;
    }

    private static void saveToLocal(List<PostsPageResponse.PageBean.RowsBean> newReadList) {
        String localStr = UtilsGson.toJson(newReadList);
        SharedPreferences sp = UtilsSharedPreference.getSharedPreference(UtilsSharedPreference.UNCLEAR_SETTING);
        UtilsSharedPreference.setStringValue(sp, SAVE_TAG, localStr);
        newReadList.clear();
    }

    private static void initFromLocal() {
        SharedPreferences sp = UtilsSharedPreference.getSharedPreference(UtilsSharedPreference.UNCLEAR_SETTING);
        String localStr = UtilsSharedPreference.getStringValue(sp, SAVE_TAG);
        Type type = new TypeToken<List<PostsPageResponse.PageBean.RowsBean>>() {
        }.getType();
        readList.clear();
        if (!TextUtils.isEmpty(localStr)) readList = UtilsGson.fromJson(localStr, type);
    }
}