package com.zero.library.base.retrofit;

/**
 * Created by ZeroAries on 2016/3/18.
 * 请求返回基类
 */
public class DefaultResponse {
    private String message;
    private String errorCode;
    private String errorMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
