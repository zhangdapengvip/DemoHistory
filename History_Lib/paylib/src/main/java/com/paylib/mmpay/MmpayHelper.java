package com.paylib.mmpay;

import android.content.Context;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MmpayHelper {

	// errorcode为同步的支付结果，实际结果以服务端返回为准；
	// 0 成功 展示成功页面
	// -1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
	// -2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
	public static final int PAY_SUCCESS = 0;

	private IWXAPI mApi;
	private Context mContext;
	private MmpayOrderInfo mOrder;
	public static String APP_ID;

	public MmpayHelper(Context context, MmpayOrderInfo order) {
		this.mContext = context;
		this.mOrder = order;
		mApi = WXAPIFactory.createWXAPI(mContext, mOrder.appId);
		mApi.registerApp(mOrder.appId);
		Constants.APP_ID = mOrder.appId;
		APP_ID = mOrder.appId;
	}

	public void pay() {
		PayReq req = new PayReq();
		req.appId = mOrder.appId;
		req.openId = mOrder.appId;
		req.partnerId = mOrder.partner;
		req.prepayId = mOrder.prepayId;
		req.nonceStr = mOrder.nonceStr;
		req.timeStamp = mOrder.timeStamp;
		req.packageValue = mOrder.packageValue;
		req.sign = mOrder.sign;
		mApi.sendReq(req);
	}

	public static boolean isInstallMm(Context context, String appId) {
		return WXAPIFactory.createWXAPI(context, appId).getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
	}
}
