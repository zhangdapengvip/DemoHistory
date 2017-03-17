package com.paylib;

public class ErrorInfo {
	public static String getErrorInfo(String resultCode) {
		if (resultCode.equals("9000")) {
			return "操作成功";
		} else if (resultCode.equals("8000")) {
			return "您的付款正在处理中";
		} else if (resultCode.equals("4000")) {
			return "您取消了付款";
		} else if (resultCode.equals("4001")) {
			return "您取消了付款";
		} else if (resultCode.equals("4003")) {
			return "您的支付宝被冻结";
		} else if (resultCode.equals("4004")) {
			return "您取消了付款";
		} else if (resultCode.equals("4005")) {
			return "您取消了付款";
		} else if (resultCode.equals("4006")) {
			return "您取消了付款";
		} else if (resultCode.equals("4010")) {
			return "请重新绑定账户";
		} else if (resultCode.equals("6000")) {
			return "支付系统正在升级";
		} else if (resultCode.equals("6001")) {
			return "您取消了付款";
		} else if (resultCode.equals("6002")) {
			return "网络连接失败";
		} else {
			return "您取消了付款";
		}
	}
}
