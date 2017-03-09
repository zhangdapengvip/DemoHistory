package com.printer.bean;

/**
 * Created by ZeroAries on 2016/1/15.
 */
public class BloodSugarInfo {

    private String result;

    public BloodSugarInfo() {
    }

    public void loadData(byte[] srcArr) {
        if (srcArr.length >= 5) {
            int value1 = srcArr[3];
            int value2 = srcArr[4];
            result = 256.0F * value1 / 10.0F + value2 / 10.0F + "";
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
