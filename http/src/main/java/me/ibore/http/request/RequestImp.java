package me.ibore.http.request;

import java.util.List;
import java.util.Map;

import me.ibore.http.headers.Headers;
import me.ibore.http.headers.HttpHeaders;
import me.ibore.http.params.HttpParams;
import me.ibore.http.params.Params;

/**
 * Created by Administrator on 2017/6/2.
 */

public abstract class RequestImp implements Request {

    private Headers headers;
    private Params params;
    private Object tag;

    public RequestImp() {
        headers = new HttpHeaders();
        params = new HttpParams();
    }

    @Override
    public Request headers(Headers headers) {
        headers.addAll(headers);
        return this;
    }

    @Override
    public Request header(String key, String value) {
        header(key, value, false);
        return this;
    }

    @Override
    public Request header(String key, String value, boolean isReplace) {
        if (isReplace) headers.set(key, value);
        else headers.add(key, value);
        return this;
    }

    @Override
    public Request removeHeaders() {
        headers.clear();
        return this;
    }

    @Override
    public Request removeHeader(String key) {
        headers.remove(key);
        return this;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers.getHeaders();
    }

    @Override
    public Request params(Params params) {
        params.addAll(params);
        return this;
    }

    @Override
    public Request param(String key, String value) {
        param(key, value, false);
        return this;
    }

    @Override
    public Request param(String key, String value, boolean isReplace) {
        if (isReplace) params.set(key, value);
        else params.add(key, value);
        return this;
    }

    @Override
    public Request removeParams() {
        params.clear();
        return this;
    }

    @Override
    public Request removeParam(String key) {
        params.remove(key);
        return this;
    }

    @Override
    public Map<String, List<String>> getParams() {
        return params.getParams();
    }

    @Override
    public Request tag(Object tag) {
        this.tag = tag;
        return this;
    }

}
