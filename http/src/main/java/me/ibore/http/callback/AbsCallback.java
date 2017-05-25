package me.ibore.http.callback;

import java.io.IOException;

import me.ibore.http.XHttp;
import me.ibore.http.request.BaseRequest;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class AbsCallback<T> {

    public void onStart(BaseRequest request) {}

    public void onComplete() {}

    public void onResponse(final Call call, Response response) throws IOException {
        final T t = convert(response);
        XHttp.getInstance().getDelivery().post(new Runnable() {
            @Override
            public void run() {
                onSuccess(call, t);
            }
        });
    }

    protected abstract void onSuccess(Call call, T t);

    abstract T convert(Response response) throws IOException;
}
