package me.ibore.http;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-06-04 02:29
 * website: ibore.me
 */

public final class HttpHeaders {

    private LinkedHashMap<String, String> headersMap;

    public HttpHeaders() {
        headersMap = new LinkedHashMap<>();
    }

    public void putAll(HttpHeaders httpHeaders) {
        headersMap.putAll(httpHeaders.getHeadersMap());
    }

    public void put(String key, String value) {
        headersMap.put(key, value);
    }

    public void get(String key) {
        headersMap.get(key);
    }

    public void remove(String key) {
        headersMap.remove(key);
    }

    public void clear() {
        headersMap.clear();
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }
}
