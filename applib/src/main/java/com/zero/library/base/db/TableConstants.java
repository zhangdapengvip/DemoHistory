package com.zero.library.base.db;

/**
 * Created by ZeroAries on 16/3/20.
 * 创建表常量类
 */
public class TableConstants {
    /**
     * 通用ID
     */
    public static final String TABLE_ID = "_id";

    /**
     * 本地缓存表
     */
    public static final String TABLE_NAME_LOCALCACHE = "local_cache";
    public static final String TABLE_CACHE_KEY = "key";
    public static final String TABLE_CACHE_CONTENT = "content";
    public static final String CREATE_LOCALCATCH =
            "CREATE TABLE " + TABLE_NAME_LOCALCACHE + " (" +
                    TABLE_ID + " integer primary key autoincrement, " +
                    TABLE_CACHE_KEY + " TEXT, " +
                    TABLE_CACHE_CONTENT + " TEXT)";

}
