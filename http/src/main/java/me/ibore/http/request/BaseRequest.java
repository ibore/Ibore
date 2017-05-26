package me.ibore.http.request;

import java.io.IOException;
import java.util.LinkedHashMap;

import me.ibore.http.call.AbsCall;
import me.ibore.http.callback.AbsCallback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-26 00:51
 * website: ibore.me
 */

public abstract class BaseRequest {

    protected Request.Builder builder;
    protected String url;
    protected String tag;
    protected LinkedHashMap<String, String> params;

    public BaseRequest(String url) {
        this.url = url;
        builder = new Request.Builder();
        params = new LinkedHashMap<>();
    }

    public BaseRequest param(String key, String value) {
        params.put(key, value);
        return this;
    }

    public BaseRequest tag(Object tag) {
        builder.tag(tag);
        return this;
    }

    abstract Request generateRequest();

    protected AbsCall getCall(BaseRequest baseRequest, Request request) {
        return AbsCall.create(baseRequest, request);
    }

    public void execute(AbsCallback absCallback) {
        getCall(this, generateRequest()).enqueue(absCallback);
    }

    public Response execute() throws IOException {
        return getCall(this, generateRequest()).execute();
    }
}
