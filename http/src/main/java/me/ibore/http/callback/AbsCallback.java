package me.ibore.http.callback;

import me.ibore.http.call.Call;
import me.ibore.http.request.BaseRequest;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class AbsCallback<T> {

    public void onStart(BaseRequest request) {}

    public abstract T convert(Call call, Response response) throws Exception;

    public abstract void onSuccess(T t);

    public abstract void onError(Call call, Exception e);

    public void onComplete() {}

}
