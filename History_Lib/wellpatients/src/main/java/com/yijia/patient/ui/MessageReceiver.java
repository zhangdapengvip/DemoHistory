package com.yijia.patient.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yijia.patient.ui.activity.MainTabActivity;
import com.yijia.patient.ui.manager.UserManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ZeroAries on 2016/4/5.
 * JPush接收广播
 */
public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String mPushExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        Logger.d("onReceive" + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            // SDK 向 JPush Server 注册所得到的注册 全局唯一的 ID
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Logger.d("接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 接收到推送下来的自定义消息
            Logger.d("接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d("用户点击打开了通知");
            // 打开自定义的Activity
            if (!TextUtils.isEmpty(UserManager.getLoginInfo().getDatas().getPkUser())) {
                Intent i = new Intent(context, MainTabActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            // Rich Push,推送
            // Web页面、图片、声音等除普通文本之外更丰富的内容。JPushInterface.EXTRA_EXTRA获取内容
            Logger.d("用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            // 连接状态
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Logger.w("[MessageReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // 接收到推送下来的通知
            if (mPushExtra.contains("ETC")) {
                Logger.e(mPushExtra + "---清除消息");
                JPushInterface.clearNotificationById(context, notifactionId);
            }
            Logger.d("[MessageReceiver] 接收到推送下来的通知");
            Logger.d("[MessageReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else {
            Logger.d("[MessageReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MessageCustomDeatilActiviy.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(
//                    MessageCustomDeatilActiviy.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MessageCustomDeatilActiviy.KEY_MESSAGE, message);
//            if (!TextUtils.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (null != extraJson && extraJson.length() > 0) {
//                        msgIntent.putExtra(
//                                MessageCustomDeatilActiviy.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            context.sendBroadcast(msgIntent);
//        }
    }
}