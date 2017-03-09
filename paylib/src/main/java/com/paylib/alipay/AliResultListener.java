package com.paylib.alipay;

public interface AliResultListener {
	// result为同步的支付结果，实际结果以服务端返回为准；
    void onResult(String errorCode);
}
