package com.paylib.unionpay;

import android.app.Activity;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class UnionpayHelper {
	private Activity mActivitiy;
	public static final String UNION_PAY_MODEL = "00";

	public UnionpayHelper(Activity activity) {
		this.mActivitiy = activity;
	}

	public void pay(String tn, String mode) {
		UPPayAssistEx.startPayByJAR(mActivitiy, PayActivity.class, null, null,
				tn, mode);
	}
	
/**结果回调
	    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	        if (resultCode == Activity.RESULT_OK) {
	            if (requestCode == 10) {
	                String payResult = intent.getExtras().getString("pay_result");
	                if ("success".equals(payResult)) {
	                                                      银联支付成功
	                } else if ("fail".equals(payResult)) {
	                	银联支付失败
	                } else if ("cancel".equals(payResult)) {
	                	银联支付取消
	                }
	            }
	        }
	  }
**/
}
