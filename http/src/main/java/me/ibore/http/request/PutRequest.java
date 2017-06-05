package me.ibore.http.request;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/6/5.
 */

public final class PutRequest extends BodyRequest<PutRequest> {

    public PutRequest(String url) {
        super(url, "PUT");
    }

    @Override
    protected Request generateRequest() {
        Request.Builder builder = new Request.Builder();
        return builder.put(generateRequestBody())
                .url(getUrl())
                .headers(generateHeaders(getHeaders()))
                .tag(getTag())
                .build();
    }
}
