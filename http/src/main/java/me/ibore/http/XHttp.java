package me.ibore.http;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import me.ibore.http.request.GetRequest;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/25.
 */

public class XHttp {

    public static final int DEFAULT_MILLISECONDS = 60000;       //默认的超时时间

    private static Application context;

    private Handler mDelivery;
    private OkHttpClient.Builder okHttpClientBuilder;           //ok请求的客户端
    private OkHttpClient okHttpClient;                          //ok请求的客户端
    private static XHttp xHttp;


    public static GetRequest.Builder get(String url) {
        return new GetRequest.Builder(url);
    }

    private XHttp() {
        okHttpClientBuilder = new OkHttpClient.Builder();
//        okHttpClientBuilder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public Handler getDelivery() {
        return mDelivery;
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
    /** 获取全局上下文 */
    public static Context getContext() {
        if (context == null) throw new IllegalStateException("请先在全局Application中调用 OkGo.init() 初始化！");
        return context;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return okHttpClientBuilder;
    }

    public OkHttpClient getOkHttpClient() {
        if (null == okHttpClient) okHttpClient = okHttpClientBuilder.build();
        return okHttpClient;
    }


}
