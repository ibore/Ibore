package me.ibore.http.request;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/2.
 */

public final class GetRequest extends Request<GetRequest> {

    public GetRequest(String url) {
        super(url, "GET");
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    protected okhttp3.Request generateRequest() {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.get().url(generateUrl(getUrl(), getParams().getUrlParamsMap())).tag(getTag()).headers(generateHeaders(getHeaders()));
        return builder.build();
    }



}
