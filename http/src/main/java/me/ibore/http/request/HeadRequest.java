package me.ibore.http.request;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/5.
 */

public final class HeadRequest extends Request<HeadRequest> {

    public HeadRequest(String url) {
        super(url, "HEAD");
    }

    @Override
    protected okhttp3.Request generateRequest() {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.head()
                .url(generateUrl(getUrl(), getParams().getUrlParamsMap()))
                .tag(getTag())
                .headers(generateHeaders(getHeaders()));
        return builder.build();
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }
}
