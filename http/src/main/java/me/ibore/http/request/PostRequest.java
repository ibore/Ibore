package me.ibore.http.request;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-27 01:17
 * website: ibore.me
 */

public class PostRequest extends BaseRequest {

    public PostRequest(String url) {
        super(url);
        method = "POST";
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    protected Request generateRequest(RequestBody requestBody) {
        //        url = HttpUtils.createUrlFromParams(baseUrl, params);
        url = baseUrl;
        builder.post(requestBody).url(url).tag(tag).headers(headersBuilder.build());
        request = builder.build();
        return request;
    }

}
