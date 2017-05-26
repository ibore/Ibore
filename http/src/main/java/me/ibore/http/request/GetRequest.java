package me.ibore.http.request;

import java.util.Map;

import okhttp3.Request;


/**
 * Created by Administrator on 2017/5/25.
 */

public class GetRequest extends BaseRequest {

    private Request request;

    public GetRequest(String url) {
        super(url);
    }

    @Override
    public Request generateRequest() {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
        else sb.append("?");
        for (Map.Entry<String, String> param : params.entrySet()) {
            sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        builder.url(sb.toString()).tag(tag);
        request = builder.build();
        return request;
    }
}
