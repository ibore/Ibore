package me.ibore.http.request;

import me.ibore.http.call.AbsCall;
import me.ibore.http.callback.AbsCallback;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GetRequest implements BaseRequest {

    private final Request.Builder builder;

    private volatile CacheControl cacheControl; // Lazily initialized.

    private Request request;

    public GetRequest(String url) {
        builder = new Request.Builder();
        builder.url(url);
    }

    public GetRequest headers(Headers headers) {
        builder.headers(headers);
        return this;
    }

    public GetRequest tag(Object tag) {
        builder.tag(tag);
        return this;
    }

    private AbsCall getCall(BaseRequest baseRequest, Request request) {
        return AbsCall.create(baseRequest, request);
    }

    public void enqueue(AbsCallback absCallback) {
        request = builder.build();
        getCall(this, request).enqueue(absCallback);
    }

}
