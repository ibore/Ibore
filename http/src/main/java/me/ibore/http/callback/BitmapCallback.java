package me.ibore.http.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25.
 */

public abstract class BitmapCallback extends AbsCallback<Bitmap> {

    @Override
    public Bitmap convert(Response response) throws Exception {
        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        return bitmap;
    }
}
