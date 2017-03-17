package com.zero.library.base.retrofit;


import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.PropertyManager;
import com.zero.library.base.utils.UtilsBase64;
import com.zero.library.base.utils.UtilsSharedPreference;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZeroAries on 2016/3/14.
 * 初始化网络请求
 */
public class RetrofitFactory {
    public static final String BASE_HOST = PropertyManager.getRequestHost();
    public static final int TIME_OUT = 30;

    public static <T> T getRetorfit(Class<T> cls) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(
                PropertyManager.logEnable() ?
                        HttpLoggingInterceptor.Level.BODY :
                        HttpLoggingInterceptor.Level.NONE);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
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
            }
        };

        Interceptor catchInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                String cache = request.header("Cache-Time");
                if (TextUtils.isEmpty(cache)) {//缓存时间不为空
                    Response response1 = response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "max-age="+cache)
                            .build();
                    return response1;
                } else {
                    return response;
                }
            }
        };
        //

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
//                .addInterceptor(catchInterceptor)//缓存拦截器
//                .cache(new Cache(new File(DirConstants.DEFAULT_CATCH_DIR), 10240 * 1024))//缓存空间10M
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //OKhttp 开启Log
        if (PropertyManager.logEnable()) builder.addInterceptor(loggingInterceptor);

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(client)
                .build();
        return retrofit.create(cls);
    }

    /**
     * GZIP压缩请求数据
     *
     * @param body
     * @return
     */
    private static RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }

    /**
     * @return Auth认证信息
     */
    private static String getAuth() {
        byte[] authInfo = UtilsSharedPreference.getStringValue(AppConstants.SP_AUTH_INFO).getBytes();
        return "Basic " + UtilsBase64.encodeToString(authInfo);
    }
}
