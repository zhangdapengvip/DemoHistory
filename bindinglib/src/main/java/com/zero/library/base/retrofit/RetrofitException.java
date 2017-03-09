package com.zero.library.base.retrofit;

/**
 * Created by ZeroAries on 2016/4/1.
 * Retrofi异常
 */
public class RetrofitException extends Exception {
    public RetrofitException() {
    }

    public RetrofitException(String detailMessage) {
        super(detailMessage);
    }

    public RetrofitException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RetrofitException(Throwable throwable) {
        super(throwable);
    }
}
