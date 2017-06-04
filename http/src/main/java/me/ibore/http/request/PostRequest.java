package me.ibore.http.request;


import okhttp3.RequestBody;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-27 01:17
 * website: ibore.me
 */

public class PostRequest extends BodyRequest<PostRequest> {

    public PostRequest(String url) {
        super(url, "POST");
    }

    @Override
    protected okhttp3.Request generateRequest(RequestBody requestBody) {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.post(generateRequestBody()).url(getUrl()).tag(getTag()).headers(generateHeaders());
        return builder.build();
    }

}
