package me.ibore.http;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import me.ibore.http.interceptor.HttpLoggingInterceptor;
import me.ibore.http.request.GetRequest;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/25.
 */

public class XHttp {

    private static Application context;
    private Handler mDelivery;
    private OkHttpClient.Builder okHttpClientBuilder;           //ok请求的客户端
    private OkHttpClient okHttpClient;                          //ok请求的客户端
    private static XHttp xHttp;

    public XHttp() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public static void init(Application app) {
        context = app;
    }

    public static XHttp getInstance() {
        if (null == xHttp) {
            xHttp = new XHttp();
        }
        return xHttp;
    }


    public Handler getDelivery() {
        return mDelivery;
    }

    public XHttp debug(final String tag) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger.DEFAULT);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        return this;
    }

    /** 获取全局上下文 */
    public static Context getContext() {
        if (context == null) throw new IllegalStateException("请先在全局Application中调用 OkGo.init() 初始化！");
        return context;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return okHttpClientBuilder;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) okHttpClient = okHttpClientBuilder.build();
        return okHttpClient;
    }

    public static GetRequest get(String url) {
        return new GetRequest(url);
    }
}
