package me.ibore.http.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.ibore.http.HttpHeaders;
import me.ibore.http.HttpParams;
import me.ibore.http.call.AbsCall;
import me.ibore.http.callback.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/2.
 */

public abstract class Request<R extends Request> {

    private String url;
    private String method;
    private HttpHeaders httpHeaders;
    private HttpParams httpParams;
    private Object tag;

    private Request() {}

    public Request(String url, String method) {
        this.url = url;
        this.method = method;
        httpHeaders = new HttpHeaders();
        httpParams = new HttpParams();
    }

    protected String getUrl() {
        return url;
    }

    protected String getMethod() {
        return method;
    }

    public R headers(HttpHeaders httpHeaders) {
        httpHeaders.putAll(httpHeaders);
        return (R) this;
    }

    public R header(String key, String value) {
        httpHeaders.put(key, value);
        return (R) this;
    }

    public R removeHeaders() {
        httpHeaders.clear();
        return (R) this;
    }

    public R removeHeader(String key) {
        httpHeaders.remove(key);
        return (R) this;
    }

    protected HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public R params(HttpParams httpParams) {
        httpParams.putAll(httpParams);
        return (R) this;
    }

    public R param(String key, String value, boolean... isReplace) {
        httpParams.put(key, value, isReplace);
        return (R) this;
    }

    public R removeParams() {
        httpParams.clear();
        return (R) this;
    }

    public R removeParam(String key) {
        httpParams.remove(key);
        return (R) this;
    }

    protected HttpParams getParams() {
        return httpParams;
    }

    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    protected Object getTag() {
        return tag;
    }

    protected okhttp3.Headers generateHeaders(HttpHeaders headers) {
        okhttp3.Headers.Builder builder = new okhttp3.Headers.Builder();
        try {
            for (Map.Entry<String, String> urlParams : headers.getHeadersMap().entrySet()) {
                String value = urlParams.getValue();
                String urlValue = URLEncoder.encode(value, "UTF-8");
                builder.add(urlParams.getKey(), urlValue);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    protected String generateUrl(String url, LinkedHashMap<String, List<String>> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
            else sb.append("?");
            for (Map.Entry<String, List<String>> urlParams : params.entrySet()) {
                List<String> urlValues = urlParams.getValue();
                for (String value : urlValues) {
                    //对参数进行 utf-8 编码,防止头信息传中文
                    String urlValue = URLEncoder.encode(value, "UTF-8");
                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public Response execute() throws IOException {
        return null;
    }

    public void execute(Callback callback) {
        AbsCall.create(generateRequest()).enqueue(callback);
    }

    protected abstract okhttp3.Request generateRequest();

    protected abstract RequestBody generateRequestBody();

}
