package com.http.factory;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZeroAries on 2016/1/12.
 * Retrofit工厂类
 */
public class RetrofitFactory {
    public static final String BASE_HOST = "http://120.26.47.172:9000/community/rest/v1/";
    public static final int TIME_OUT = 30;

    public static <T> T getRetorfit(Class<T> cls) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = chain -> {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("Authorization", getAuth())
                    .addHeader("isApp", "true")
                    .build();
//            GZIP压缩请求数据使用
//            newRequest = request.newBuilder()
//                    .addHeader("Authorization", getAuth())
//                    .addHeader("isApp", "true")
//                    .header("Content-Encoding", "gzip")
//                    .method(request.method(), gzip(request.body()))
//                    .build();
            return chain.proceed(newRequest);
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(client)
                .build();
        return retrofit.create(cls);
    }

    /**
     * @return Auth认证信息
     */
    private static String getAuth() {
        return "Basic " + "emhhbmdkYTpxcXFxcXE=";
    }
}
