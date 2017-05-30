package me.ibore.http.request;


import java.io.IOException;

import me.ibore.http.HttpParams;
import me.ibore.http.XHttp;
import me.ibore.http.call.AbsCall;
import me.ibore.http.callback.AbsCallback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-26 00:51
 * website: ibore.me
 */

public abstract class BaseRequest {

    protected Request request;
    protected XHttp xHttp;
    protected String baseUrl;
    protected Request.Builder builder;
    protected Headers.Builder headersBuilder;
    protected String method;
    protected String url;
    protected String tag;
    protected HttpParams httpParams;
    private AbsCallback callback;

    public BaseRequest(String url) {
        baseUrl = url;
        builder = new Request.Builder();
        xHttp = XHttp.getInstance();
        headersBuilder = new Headers.Builder();
        httpParams = new HttpParams();
        httpParams.put(xHttp.getParams());
    }

    public BaseRequest tag(Object tag) {
        builder.tag(tag);
        return this;
    }

    public BaseRequest header(String name, String value) {
        headersBuilder.set(name, value);
        return this;
    }

    public BaseRequest addHeader(String name, String value) {
        headersBuilder.add(name, value);
        return this;
    }

    public BaseRequest removeHeader(String name) {
        headersBuilder.removeAll(name);
        return this;
    }

    public BaseRequest headers(Headers headers) {
        this.headersBuilder = headers.newBuilder();
        return this;
    }

    public BaseRequest param(String key, String value, boolean... isReplace) {
        httpParams.put(key, value, isReplace);
        return this;
    }

    protected abstract RequestBody generateRequestBody();

    protected abstract Request generateRequest(RequestBody requestBody);

    /** 对请求body进行包装，用于回调上传进度 */
    public RequestBody wrapRequestBody(RequestBody requestBody) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody);
        progressRequestBody.setListener(new ProgressRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength, final long networkSpeed) {
                xHttp.getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) callback.upProgress(bytesWritten, contentLength, bytesWritten * 1.0f / contentLength, networkSpeed);
                    }
                });
            }
        });
        return progressRequestBody;
    }


    protected AbsCall generateCall(BaseRequest baseRequest, Request request) {
        return AbsCall.create(baseRequest, request);
    }

    public void execute(AbsCallback absCallback) {
        this.callback = absCallback;
        generateCall(this, generateRequest(wrapRequestBody(generateRequestBody()))).enqueue(absCallback);
    }

    public Response execute() throws IOException {
        return generateCall(this, generateRequest(wrapRequestBody(generateRequestBody()))).execute();
    }
}
