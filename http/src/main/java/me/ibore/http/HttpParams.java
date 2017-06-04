package me.ibore.http;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MediaType;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-06-04 01:45
 * website: ibore.me
 */

public final class HttpParams {

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");


    private LinkedHashMap<String, List<String>> urlParamsMap = new LinkedHashMap<>();

    private LinkedHashMap<String, File> fileParamsMap = new LinkedHashMap<>();

    public HttpParams() {
        urlParamsMap = new LinkedHashMap<>();
    }

    public void putAll(HttpParams httpParams) {
        if (null != httpParams && !httpParams.getUrlParamsMap().isEmpty())
            this.urlParamsMap.putAll(httpParams.getUrlParamsMap());
    }

    public void put(String key, int value, boolean... isReplace) {
        put(key, String.valueOf(value), isReplace);
    }

    public void put(String key, char value, boolean... isReplace) {
        put(key, String.valueOf(value), isReplace);
    }

    public void put(String key, double value, boolean... isReplace) {
        put(key, String.valueOf(value), isReplace);
    }

    public void put(String key, boolean value, boolean... isReplace) {
        put(key, String.valueOf(value), isReplace);
    }

    public void put(String key, String value, boolean... isReplace) {
        List<String> urlValues = urlParamsMap.get(key);
        if (urlValues == null) {
            urlValues = new ArrayList<>();
            urlParamsMap.put(key, urlValues);
        }
        if (null != isReplace && isReplace.length > 0) {
            if (isReplace[0]) urlValues.clear();
        }
        urlValues.add(value);
    }

    public String get(String key, int postion) {
        return urlParamsMap.get(key).get(postion);
    }

    public List<String> get(String key) {
        return urlParamsMap.get(key);
    }

    public void remove(String key) {
        urlParamsMap.remove(key);
    }

    public void clear() {
        urlParamsMap.clear();
    }

    public LinkedHashMap<String, List<String>> getUrlParamsMap() {
        return urlParamsMap;
    }



}
