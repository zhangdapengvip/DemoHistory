package com.zero.library.base.manager;

import com.zero.library.base.AppProperty;

public class PropertyManager {

    public static boolean logEnable() {
        return AppProperty.getInstance().checkValue("ENABLE_LOG", "TRUE");
    }

    public static String getRequestHost() {
        return AppProperty.getInstance().getParameter("HOST_ADDRESS");
    }
}
