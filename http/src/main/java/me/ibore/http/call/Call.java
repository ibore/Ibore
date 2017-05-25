package me.ibore.http.call;

import java.io.IOException;

import me.ibore.http.callback.AbsCallback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface Call<T> {
    Request request();

    Response execute() throws IOException;

    void enqueue(AbsCallback<T> absCallback);

    void cancel();

    boolean isExecuted();

    boolean isCanceled();

}
