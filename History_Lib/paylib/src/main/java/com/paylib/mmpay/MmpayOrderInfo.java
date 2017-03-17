package com.paylib.mmpay;

public class MmpayOrderInfo {
	// 微信分配的公众账号ID/必填
	public String appId = "";
	// 微信支付分配的商户号/必填
	public String partner;
	// 时间戳:标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。/必填
	public String timeStamp;
	// 微信返回的支付交易会话ID/必填
	public String prepayId;
	// 随机字符串，不长于32位。/必填
	public String nonceStr;
	// 暂填写固定值Sign=WXPay/必填
	public String packageValue = "Sign=WXPay";
	// 签名/必填
	public String sign;
}
