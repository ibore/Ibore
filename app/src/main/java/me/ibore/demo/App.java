package me.ibore.demo;

import android.app.Application;

import me.ibore.http.Params;
import me.ibore.http.XHttp;

/**
 * Created by Administrator on 2017/5/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XHttp.init(this);
        Params params = new Params();
        params.put("Test", "Test");
        params.put("Test", "Test");
        XHttp.getInstance().debug("XHttp").addParams(params);
    }
}
