package me.ibore.http.call;

import android.util.Log;

import java.io.IOException;

import me.ibore.http.XHttp;
import me.ibore.http.callback.Callback;
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

    private okhttp3.Call call;

    public AbsCall(Request request) {
        call = XHttp.getInstance().getOkHttpClient().newCall(request);
    }

    @Override
    public Response execute() throws IOException {
        return call.execute();
    }

    @Override
    public void enqueue(final Callback callback) {
        callback.onStart();
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                XHttp.getInstance().getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(mCall, e);
                        callback.onComplete();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Object object = null;
                try {
                    object = callback.convert(mCall, response);
                } catch (final Exception e) {
                    XHttp.getInstance().getDelivery().post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(mCall, e);
                        }
                    });
                } finally {
                    XHttp.getInstance().getDelivery().post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onComplete();
                        }
                    });
                }

                final Object finalObject = object;
                XHttp.getInstance().getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(finalObject);

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


    public static AbsCall create(Request request) {
        mCall = new AbsCall(request);
        return mCall;
    }
}
