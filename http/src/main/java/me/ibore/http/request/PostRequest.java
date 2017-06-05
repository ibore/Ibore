package me.ibore.http.request;


/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-27 01:17
 * website: ibore.me
 */

public final class PostRequest extends BodyRequest<PostRequest> {

    public PostRequest(String url) {
        super(url, "POST");
    }

    @Override
    protected okhttp3.Request generateRequest() {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.post(generateRequestBody()).url(getUrl()).tag(getTag()).headers(generateHeaders(getHeaders()));
        return builder.build();
    }

}
