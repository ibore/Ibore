package me.ibore.http.request;

import java.io.IOException;

import me.ibore.http.call.AbsCall;
import me.ibore.http.callback.AbsCallback;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GetRequest implements BaseRequest {

    private Request.Builder builder;
    private String url;

    private volatile CacheControl cacheControl; // Lazily initialized.

    private Request request;

    public GetRequest(String url) {
        builder = new Request.Builder();
        this.url = url;
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


    @Override
    public BaseRequest header(String key, String value) {
        builder.header(key, value);
        return this;
    }


    @Override
    public void execute(AbsCallback absCallback) {
        request = builder.build();
        getCall(this, request).enqueue(absCallback);
    }

    @Override
    public Response execute() throws IOException {
        request = builder.build();
        return getCall(this, request).execute();
    }

    @Override
    public BaseRequest param(String key, String s) {
        return this;
    }
}
