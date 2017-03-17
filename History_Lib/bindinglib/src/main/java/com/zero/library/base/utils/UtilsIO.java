package com.zero.library.base.utils;

import com.orhanobut.logger.Logger;
import com.zero.library.base.constants.DirConstants;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UtilsIO {

    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                Logger.e(e.toString());
            }
        }
        return true;
    }

    public static void writeLog(String where, String msg, Throwable error) {
        try {
            File logFile = new File(DirConstants.DEFAULT_LOG_DIR, "error.txt");
            StringBuilder sb = new StringBuilder();
            sb.append("----------------->>>");
            sb.append(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_DETAIL));
            sb.append(" : ");
            sb.append(where);
            sb.append(msg);
            sb.append("\n");
            if (null != error) {
                sb.append("Exception:").append(error.toString());
                for (StackTraceElement ee : error.getStackTrace()) {
                    sb.append("\n").append(ee.getClassName()).append(".")
                            .append(ee.getMethodName()).append("(")
                            .append(ee.getLineNumber()).append(")");
                }
            }
            sb.append("\r\n");
            RandomAccessFile fo = new RandomAccessFile(logFile, "rw");
            fo.seek(fo.length());
            fo.write(sb.toString().getBytes());
            fo.close();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }
}
