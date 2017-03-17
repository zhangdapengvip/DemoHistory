package com.paylib.alipay;

public class AlipayOrderInfo {
	// 商户网站唯一订单号
	public String id;
	// 订单名称
	public String subject;
	// 订单详情
	public String content;
	// 订单价格
	public String price;
	// 服务器异步通知页面路径
	public String notify_url;
	// PID 签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
	public String partnerId = "2088911379302667";
	// 签约卖家支付宝账号
	public String sellId = "2088911379302667";
	// 服务接口名称， 固定值
	public String service = "mobile.securitypay.pay";
	// 商户网站使用的编码格式，固定为utf-8。
	public String input_charset = "utf-8";
	// 商户私钥，pkcs8格式
	public String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANoP1GsziynJY0w/Osbx8aWhrTe5Oa2a6yZbbxRfzulJBxOQxYw+whQ1hWx6Y4th44PbLPPhztClc1450GM8GRPnitqVhc3V0SQUeRWLnyuK8Wb7BKznVHygO8RdwiA1wHw3LgHwsYzbhmVkDEVawaI4U67vOuwnVahkw6UY0IEdAgMBAAECgYBUnr/3udDPLm9yLzdH7KuxcsQdU6umSu/luqWoamWI8eXlGdEPU2tNHFfieLwYYqw/htYy7fWr0girEBRl7fRspVldtXIIrt6tSnY59gJJnjGqxyw+NwkOefidNeK6dGRcEBilkMkRsEiys/nX4R7OrLGaEnElf1ke88OUdti1AQJBAPiMVtZNaiEDW2nIWv5gPdcA7ml3ywcMi2sV70UASyyoAaxpgzs6OjdeAmgxeRusq8gyCuGBs2rjIvEGFu2g6MUCQQDgmYBL01hXuCkMQWTTqlt1Ve1TjaIlI+P4cD+7Q6QApu3QYJUpcNsK9/V/ok7+rB3cmbig0Q8EdCYjRFuSiEx5AkBn79gIegsdjxfVHrHemkSB+qO4ex3t7rMGrv+F7V+CZyd8AAue4vShXrDG/ZlVl1mmuDaQovpyglWEUTWZXaa5AkEAzuDVagKcJwB83ssFYmpSn/usBQA60FcEz3wlWApv8CHSf9PW0TsgqDPJr+0DMlW7k80MYSTv3jxWPLnkV06wkQJAXsoo5+0mY3OxWBVMlMUejUXkEcXIQLSbiiGzeXQ2fchqBJd79478Ntfe13ntV5IaoUC0f62hZgJTZIQ7wDT4YQ==";
	// 支付宝公钥
	public String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
