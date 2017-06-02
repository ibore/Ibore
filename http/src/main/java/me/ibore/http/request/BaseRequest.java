package me.ibore.http.request;


import java.io.IOException;

import me.ibore.http.XHttp;
import me.ibore.http.call.AbsCall;
import me.ibore.http.callback.AbsCallback;
import me.ibore.http.headers.Headers;
import me.ibore.http.headers.HttpHeaders;
import me.ibore.http.params.HttpParams;
import me.ibore.http.params.Params;
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

    protected String method;
    protected String url;
    protected String tag;

    protected Headers headers;
    protected Params params;
    private AbsCallback callback;

    public BaseRequest(String url) {
        baseUrl = url;
        builder = new Request.Builder();
        xHttp = XHttp.getInstance();
        headers = new HttpHeaders();
        params = new HttpParams();
        params.addAll(xHttp.getParams());
    }

    public BaseRequest tag(Object tag) {
        builder.tag(tag);
        return this;
    }

    public BaseRequest header(String name, String value) {
        headers.set(name, value);
        return this;
    }

    public BaseRequest addHeader(String name, String value) {
        headers.add(name, value);
        return this;
    }

    public BaseRequest removeHeader(String name) {
        headers.remove(name);
        return this;
    }

    public BaseRequest headers(Headers headers) {
        headers.addAll(headers);
        return this;
    }

    public BaseRequest param(String key, String value, boolean... isReplace) {
        if (null == isReplace) {
            params.set(key, value);
        } else {
            params.add(key, value);
        }
        return this;
    }

    protected abstract RequestBody generateRequestBody();

    protected abstract Request generateRequest(RequestBody requestBody);

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
