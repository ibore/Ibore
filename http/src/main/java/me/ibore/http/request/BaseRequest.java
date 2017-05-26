package me.ibore.http.request;

import java.io.IOException;

import me.ibore.http.callback.AbsCallback;
import okhttp3.Response;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-26 00:51
 * website: ibore.me
 */

public interface BaseRequest {
    BaseRequest header(String key, String value);
    void execute(AbsCallback absCallback);
    Response execute() throws IOException;

    BaseRequest param(String key, String s);
}
