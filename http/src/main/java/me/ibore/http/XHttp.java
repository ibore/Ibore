package me.ibore.http;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/25.
 */

public class XHttp {

    private static Application context;
    private OkHttpClient.Builder okHttpClientBuilder;           //ok请求的客户端
    private OkHttpClient okHttpClient;                          //ok请求的客户端
    private static XHttp xHttp;

    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public static void init(Application app) {
        context = app;
    }

    public static XHttp getInstance() {
        synchronized (xHttp) {
            if (null == xHttp) {
                xHttp = new XHttp();
            }
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
        return okHttpClient;
    }


    public static Request.Builder get() {
        return new Request.Builder();
    }
}
