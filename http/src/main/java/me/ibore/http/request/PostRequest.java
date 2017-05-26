package me.ibore.http.request;

import okhttp3.Request;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-27 01:17
 * website: ibore.me
 */

public class PostRequest extends BaseRequest {

    public PostRequest(String url) {
        super(url);
    }

    @Override
    Request generateRequest() {
        return null;
    }
}
