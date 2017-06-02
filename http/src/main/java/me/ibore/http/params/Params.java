package me.ibore.http.params;

import java.util.List;
import java.util.Map;

import me.ibore.http.map.MultiValuedMap;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2017/6/2.
 */

public interface Params extends MultiValuedMap<String, String> {

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    void addAll(Params params);

    void setAll(Params params);

    Map<String, List<String>> getParams();
}
