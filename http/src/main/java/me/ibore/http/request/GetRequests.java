package me.ibore.http.request;

import me.ibore.http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2017/5/25.
 */

public class GetRequests extends BaseRequest {

    public GetRequests(String url) {
        super(url);
        method = "GET";
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    protected Request generateRequest(RequestBody requestBody) {
        url = HttpUtils.createUrlFromParams(baseUrl, params);
        builder.get().url(url).tag(tag);
        request = builder.build();
        return request;
    }

}
