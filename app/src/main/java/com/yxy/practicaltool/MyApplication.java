package com.yxy.practicaltool;

import android.app.Application;
import android.content.Context;

import com.wzgiceman.rxretrofitlibrary.BuildConfig;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class MyApplication extends Application {

    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        RxRetrofitApp.init(this, BuildConfig.DEBUG);
    }

    public static  Context getAppContext(){
        return app;
    }
}
