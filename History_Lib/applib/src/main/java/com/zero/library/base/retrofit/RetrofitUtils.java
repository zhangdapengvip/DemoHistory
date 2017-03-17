package com.zero.library.base.retrofit;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.zero.library.R;
import com.zero.library.base.AppToast;
import com.zero.library.base.utils.UtilsUi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/3/15.
 * Retrofit请求工具类
 */
public class RetrofitUtils {
    private static String TAG_REQUEST = "requesting";

    public static <T> Subscriber<? super T> request(Activity activity,
                                                    Observable<T> observable) {
        return request(activity, observable, null, null);
    }

    public static <T> Subscriber<? super T> request(Activity activity,
                                                    Observable<T> observable,
                                                    ResponseListener<T> listener) {
        return request(activity, observable, null, listener);
    }

    public static <T> Subscriber<? super T> request(Activity activity,
                                                    Observable<T> observable,
                                                    ResponseListener<T> listener,
                                                    final boolean showErrorMessage) {
        return request(activity, observable, null, listener, showErrorMessage);
    }

    public static <T> Subscriber<? super T> request(final Activity activity,
                                                    Observable<T> observable,
                                                    final View view,
                                                    final ResponseListener<T> listener) {
        return request(activity, observable, view, listener, true);
    }

    public static <T> Subscriber<? super T> request(final Activity activity,
                                                    Observable<T> observable,
                                                    final View view,
                                                    final ResponseListener<T> listener,
                                                    final boolean showErrorMessage) {
        if (!UtilsUi.isNetworkConnected(activity)) AppToast.show(activity, R.string.error_not_network);
        if (null != listener) listener.beforRequest();
        if (null != view) view.setTag(R.id.tag_request_view, TAG_REQUEST);
        Subscriber<? super T> subs = new Subscriber<T>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (activity.isFinishing()) return;
                if (null != view) view.setTag(R.id.tag_request_view, "");
                if (null != listener) listener.onFinish(false);
                if (null != listener) listener.onError(e);
                if (!TextUtils.isEmpty(e.getMessage()) &&
                        e.getMessage().contains("Unauthorized")) {
                    if (showErrorMessage) AppToast.show(activity, R.string.error_login_notice);
                } else {
                    if (showErrorMessage) AppToast.show(activity, R.string.error_service_response);
                }
            }

            @Override
            public void onNext(T t) {
                if (activity.isFinishing()) return;
                if (null != view) view.setTag(R.id.tag_request_view, "");
                if (null != listener) listener.onFinish(true);
                DefaultResponse response = (DefaultResponse) t;
                String message = response.getMessage();
                if (TextUtils.isEmpty(response.getErrorCode())) {
                    if (null != listener) listener.onSuccess(t);
                } else {
                    AppToast.show(activity, message);
                    if (null != listener) listener.onError(new RetrofitException(message));
                }
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subs);
        return subs;
    }

    /**
     * Retrofit上传文件参数获取,将参数内容填充至{@link RequestBody}
     * <pre>{@code
     * &#64;Multipart
     * &#64;POST("/upload")
     * Observable<> upload(@PartMap Map<String, RequestBody> paramsMap);
     * }</pre>
     *
     * @param parameterMap 请求参数
     * @return 请求Map
     */
    public static Map<String, RequestBody> getRequesetMap(Map<String, Object> parameterMap) {
        Map<String, RequestBody> map = new HashMap<>();
        Set<Map.Entry<String, Object>> set = parameterMap.entrySet();
        for (Map.Entry<String, Object> item : set) {
            String key = item.getKey();
            Object value = item.getValue();
            RequestBody body;
            if (value instanceof String) {
                body = RequestBody.create(MediaType.parse("text/plain"), (String) value);
                map.put(key, body);
            } else if (value instanceof File) {
                File file = (File) value;
                String mime = getMIME(file.getName());
                if (TextUtils.isEmpty(mime)) throw new RuntimeException("Please add " + file.getName() + " MIME 类型");
                body = RequestBody.create(MediaType.parse(mime), file);
                map.put(key + "\"; filename=\"" + file.getName() + "", body);
            }
        }
        return map;
    }

    /**
     * 获取文件扩展名对应的MIME
     *
     * @param fileName FileName
     * @return MIME
     */
    private static String getMIME(String fileName) {
        String mime = "";
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        switch (ext) {
            case "jpg":
            case "jpeg":
                mime = "image/jpeg";
                break;
            case "png":
                mime = "image/png";
                break;
            case "zip":
                mime = "application/zip";
                break;
        }
        return mime;
    }

    public static final void cancle(Subscriber sbuscriber) {
        if (null != sbuscriber) {
            sbuscriber.unsubscribe();
        }
    }

    public interface ResponseListener<T> {
        void beforRequest();

        void onSuccess(T t);

        void onError(Throwable e);

        void onFinish(boolean isSuccess);
    }
}
