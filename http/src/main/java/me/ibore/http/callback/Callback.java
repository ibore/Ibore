package me.ibore.http.callback;

import me.ibore.http.call.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class Callback<T> {

    public void onStart() {}

    public abstract T convert(Call call, Response response) throws Exception;

    public abstract void onSuccess(T t);

    public abstract void onError(Call call, Exception e);

    public void onComplete() {}

    public void upProgress(long bytesWritten, long contentLength, float progress, long networkSpeed) {

    }
}
