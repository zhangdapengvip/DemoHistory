package com.printer.bean;

/**
 * Created by ZeroAries on 2016/1/15.
 */
public class BloodPressureInfo {

    //错误码
    public static final int ERROR_HEART = 1;
    public static final int ERROR_DISTURB = 2;
    public static final int ERROR_GASING = 3;
    public static final int ERROR_TEST = 4;
    public static final int ERROR_POWER = 11;
    public static final int ERROR_REVISE = 12;
    public static final int ERROR_EEPROM = 14;

    //状态码
    private static final int STATE_RUNNING = -5;
    private static final int STATE_RESULT = -4;
    private static final int STATE_ERROR = -3;

    private String currentPressure;
    private String highPressure;
    private String lowPressure;
    private String heartate;
    private int errorCode;

    public BloodPressureInfo() {
    }

    public void loadData(byte[] srcArr) {
        if (srcArr.length < 3) {
            return;
        }
        byte currentState = srcArr[2];
        if (currentState == STATE_RUNNING && srcArr.length > 4) {
            currentPressure = "srcArr[3]="+srcArr[3]+"srcArr[4]="+srcArr[4]+"----"+(srcArr[3] * 256 + srcArr[4]) + "";
        }
        if (currentState == STATE_RESULT && srcArr.length > 5) {
            currentPressure = "";
            highPressure = byte2String(srcArr[3]);
            lowPressure = byte2String(srcArr[4]);
            heartate = byte2String(srcArr[5]);
        }
        if (currentState == STATE_ERROR && srcArr.length > 3) {
            currentPressure = "";
            errorCode = srcArr[3];
        }
    }

    private String byte2String(byte src) {
        int realSrc = (src < 0) ? 256 + src : src;
        return realSrc + "";
    }


    public String getErrorInfo() {
        switch (errorCode) {
            case ERROR_HEART://人体心跳信号太小或压力突降
                return "测量错误，请根据说明书，重新带好CUFF，保持安静，重新测量";
            case ERROR_DISTURB://杂讯干扰
                return "测量错误，请根据说明书，重新带好CUFF，保持安静，重新测量";
            case ERROR_GASING://充气时间过长
                return "测量错误，请根据说明书，重新带好CUFF，保持安静，重新测量";
            case ERROR_TEST:
                return "测量错误，请根据说明书，重新带好CUFF，保持安静，重新测量";
            case ERROR_POWER://电源低电压
                return "电池电量低，请更换电池";
            case ERROR_REVISE://校正异常
                return "测量错误，请根据说明书，重新带好CUFF，保持安静，重新测量";
            case ERROR_EEPROM://EEPROM异常
                return "血压计异常 ，联系你的经销商";
        }
        return "";
    }

    public String getCurrentPressure() {
        return currentPressure;
    }

    public void setCurrentPressure(String currentPressure) {
        this.currentPressure = currentPressure;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getHeartate() {
        return heartate;
    }

    public void setHeartate(String heartate) {
        this.heartate = heartate;
    }

    public String getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(String highPressure) {
        this.highPressure = highPressure;
    }

    public String getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(String lowPressure) {
        this.lowPressure = lowPressure;
    }

    public int getSTATE_ERROR() {
        return STATE_ERROR;
    }

    public int getSTATE_RESULT() {
        return STATE_RESULT;
    }

    public int getSTATE_RUNNING() {
        return STATE_RUNNING;
    }
}
