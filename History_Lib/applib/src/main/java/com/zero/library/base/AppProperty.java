package com.zero.library.base;

import java.io.InputStream;
import java.util.Properties;

import android.text.TextUtils;

public class AppProperty {

    private static final String DEFAULT_CONFIG_FILE = "/assets/config.properties";
    private Properties mProperties;
    private static AppProperty sProperty;

    public static AppProperty getInstance() {
        if (null == sProperty) {
            sProperty = new AppProperty();
        }
        sProperty.load(null);
        return sProperty;
    }

    private AppProperty() {
        mProperties = new Properties();
    }

    public void load(String path) {
        if (TextUtils.isEmpty(path)) {
            path = DEFAULT_CONFIG_FILE;
        }
        try {
            InputStream in = AppProperty.class.getResourceAsStream(path);
            mProperties.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getParameter(String key) {
        String para = null;
        if (null != mProperties) {
            para = mProperties.getProperty(key);
            if (null != para) {
                para = para.trim();
            }
        }
        return para;
    }

    public String getParameter(String key, String defaultValue) {
        String para = null;
        if (null != mProperties) {
            para = mProperties.getProperty(key, defaultValue);
            if (null != para) {
                para = para.trim();
            }
        }
        return para;
    }

    public boolean checkValue(String key, String compare) {
        getInstance();
        return compare.equals(getParameter(key));
    }
}
