package me.ibore.http.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class StringCallback extends AbsCallback<String> {

    @Override
    public String convert(Response response) throws IOException {
        String s = response.body().string();
        response.close();
        return s;
    }

}
