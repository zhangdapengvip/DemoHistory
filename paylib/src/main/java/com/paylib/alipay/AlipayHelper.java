package com.paylib.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;

public class AlipayHelper {

	public static final String PAY_SUCCESS = "9000";

	private Activity mActivity;
	private static final String ALGORITHM = "RSA";
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String DEFAULT_CHARSET = "UTF-8";

	public AlipayHelper(Activity activity) {
		this.mActivity = activity;
	}

	public void pay(AlipayOrderInfo order, AliResultListener result) {
		pay(getPayInfo(order), result);
	}

	public void pay(final String payInfo, final AliResultListener result) {
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(mActivity);
				String resultContent = alipay.pay(payInfo);
				AlipayResult payResult = new AlipayResult(resultContent);
				result.onResult(payResult.resultStatus);
			}
		};
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	public String getPayInfo(AlipayOrderInfo order) {
		String orderInfo = "partner=\"" + order.partnerId + "\"";
		orderInfo += "&service=\"" + order.service + "\"";
		orderInfo += "&seller_id=\"" + order.sellId + "\"";
		orderInfo += "&out_trade_no=\"" + order.id + "\"";
		orderInfo += "&subject=\"" + order.subject + "\"";
		orderInfo += "&body=\"" + order.content + "\"";
		orderInfo += "&total_fee=\"" + order.price + "\"";
		// 商品展示网址，可空
		// orderInfo += "&show_url=\"url\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"url\"";
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=\"" + order.notify_url + "\"";
		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";
		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"" + order.input_charset + "\"";
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		String sign = sign(orderInfo, order.privatekey);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 完整的符合支付宝参数规范的订单信息
		String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type="
				+ "\"" + "RSA" + "\"";
		return payInfo;
	}

	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
