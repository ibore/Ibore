package me.ibore.demo;

import android.app.Application;

import me.ibore.http.XHttp;
import me.ibore.http.params.HttpParams;

/**
 * Created by Administrator on 2017/5/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XHttp.init(this);
        HttpParams params = new HttpParams();
        params.add("Test", "Test");
        params.add("Test", "Test");
        XHttp.getInstance().debug(BuildConfig.DEBUG).addParams(params);
    }
}
