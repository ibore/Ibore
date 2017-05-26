package me.ibore.http.call;

import java.io.IOException;

import me.ibore.http.XHttp;
import me.ibore.http.callback.AbsCallback;
import me.ibore.http.request.BaseRequest;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-25 22:13
 * website: ibore.me
 */

public class AbsCall implements Call {

    private static AbsCall mCall;
    private BaseRequest baseRequest;
    private Request request;
    private okhttp3.Call call;

    public AbsCall(BaseRequest baseRequest, Request request) {
        this.baseRequest = baseRequest;
        this.request = request;
        call = XHttp.getInstance().getOkHttpClient().newCall(request);
    }

    @Override
    public BaseRequest request() {
        return baseRequest;
    }

    @Override
    public Response execute() throws IOException {
        return call.execute();
    }

    @Override
    public void enqueue(final AbsCallback absCallback) {
        absCallback.onStart(baseRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                XHttp.getInstance().getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        absCallback.onError(mCall, e);
                        absCallback.onComplete();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Object object = null;
                try {
                    object = absCallback.convert(mCall, response);
                } catch (final Exception e) {
                    XHttp.getInstance().getDelivery().post(new Runnable() {
                        @Override
                        public void run() {
                            absCallback.onError(mCall, e);
                        }
                    });
                } finally {
                    XHttp.getInstance().getDelivery().post(new Runnable() {
                        @Override
                        public void run() {
                            absCallback.onComplete();
                        }
                    });
                }

                final Object finalObject = object;
                XHttp.getInstance().getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        absCallback.onSuccess(finalObject);

                    }
                });
            }
        });
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }


    public static AbsCall create(BaseRequest baseRequest, Request request) {
        mCall = new AbsCall(baseRequest, request);
        return mCall;
    }
}
