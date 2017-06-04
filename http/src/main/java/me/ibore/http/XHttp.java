package me.ibore.http;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import me.ibore.http.interceptor.HttpLoggingInterceptor;
import me.ibore.http.request.GetRequest;
import me.ibore.http.request.PostRequest;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/25.
 */

public class XHttp {

    public static final long REFRESH_TIME = 100;
    private static Application context;
    private Handler mDelivery;
    private OkHttpClient.Builder okHttpClientBuilder;           //ok请求的客户端
    private OkHttpClient okHttpClient;                          //ok请求的客户端
    private static XHttp xHttp;

    private HttpParams httpParams;

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

    public XHttp debug(boolean debug) {
        if (debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger.DEFAULT);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return this;
    }

    /** 获取全局上下文 */
    public static Context getContext() {
        if (context == null) throw new IllegalStateException("请先在全局Application中调用 XHttp.init() 初始化！");
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

    public static PostRequest post(String url) {
        return new PostRequest(url);
    }















    public XHttp addParams(HttpParams httpParams) {
        if (null == this.httpParams) this.httpParams = new HttpParams();
        this.httpParams.putAll(httpParams);
        return this;
    }

    public void cancelTag(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public void cancelAll() {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    public HttpParams getParams() {
        return httpParams;
    }
}
