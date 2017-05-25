package me.ibore.http.model;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-25 22:19
 * website: ibore.me
 */

public class Response<T> {

    private final okhttp3.Response response;
    private final T body;

    public Response(okhttp3.Response response, T body) {
        this.response = response;
        this.body = body;
    }
}
