package com.zero.library.base.bean;

/**
 * Created by ZeroAries on 2016/3/22.
 * 下拉列表数据存储
 */
public class SpinnerItem {
    public SpinnerItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
