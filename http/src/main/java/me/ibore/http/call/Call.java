package me.ibore.http.call;

import java.io.IOException;

import me.ibore.http.callback.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-25 23:54
 * website: ibore.me
 */

public interface Call<T> {

    Response execute() throws IOException;

    void enqueue(Callback callback);

    void cancel();

    boolean isExecuted();

    boolean isCanceled();

    interface Factory {
        Call newCall(Request request);
    }
}
