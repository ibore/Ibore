package me.ibore.http.request;

import android.support.annotation.Nullable;

import java.util.List;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/25.
 */

public final class GetRequest {
    final HttpUrl url;
    final String method;
    final Headers headers;
    final @Nullable RequestBody body;
    final Object tag;

    private volatile CacheControl cacheControl; // Lazily initialized.

    GetRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.tag = builder.tag != null ? builder.tag : this;
    }

    public HttpUrl url() {
        return url;
    }

    public String method() {
        return method;
    }

    public Headers headers() {
        return headers;
    }

    public String header(String name) {
        return headers.get(name);
    }

    public List<String> headers(String name) {
        return headers.values(name);
    }

    public @Nullable RequestBody body() {
        return body;
    }

    public Object tag() {
        return tag;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Returns the cache control directives for this response. This is never null, even if this
     * response contains no {@code Cache-Control} header.
     */
    public CacheControl cacheControl() {
        CacheControl result = cacheControl;
        return result != null ? result : (cacheControl = CacheControl.parse(headers));
    }

    public boolean isHttps() {
        return url.isHttps();
    }

    @Override public String toString() {
        return "GetRequest{method="
                + method
                + ", url="
                + url
                + ", tag="
                + (tag != this ? tag : null)
                + '}';
    }

    public static class Builder {
        HttpUrl url;
        String method;
        Headers.Builder headers;
        RequestBody body;
        Object tag;

        public Builder(String url) {
            url(url);
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        Builder(GetRequest request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        private void url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            if (url.regionMatches(true, 0, "ws:", 0, 3)) {
                url = "http:" + url.substring(3);
            } else if (url.regionMatches(true, 0, "wss:", 0, 4)) {
                url = "https:" + url.substring(4);
            }
            HttpUrl parsed = HttpUrl.parse(url);
            if (parsed == null) throw new IllegalArgumentException("unexpected url: " + url);
            this.url = parsed;
        }


        public Builder header(String name, String value) {
            headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.removeAll(name);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String value = cacheControl.toString();
            if (value.isEmpty()) return removeHeader("Cache-Control");
            return header("Cache-Control", value);
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Call newCall() {

            return new RealCall(this, request, false /* for web socket */);
            return new GetRequest(this);
        }
    }
}
