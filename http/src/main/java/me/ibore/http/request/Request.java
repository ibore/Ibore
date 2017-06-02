package me.ibore.http.request;

import java.util.List;
import java.util.Map;

import me.ibore.http.callback.AbsCallback;
import me.ibore.http.headers.Headers;
import me.ibore.http.params.Params;

/**
 * Created by Administrator on 2017/6/2.
 */

public interface Request {

    Request headers(Headers headers);

    Request header(String key, String value);

    Request header(String key, String value, boolean isReplace);

    Request removeHeaders();

    Request removeHeader(String key);

    Map<String, List<String>> getHeaders();

    Request params(Params params);

    Request param(String key, String value);

    Request param(String key, String value, boolean isReplace);

    Request removeParams();

    Request removeParam(String key);

    Map<String, List<String>> getParams();

    Request tag(Object tag);

    void execute(AbsCallback absCallback);
}
