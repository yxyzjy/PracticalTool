package com.yxy.practicaltool;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wzgiceman.rxretrofitlibrary.BuildConfig;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DaoMaster;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DaoSession;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class MyApplication extends Application {

    public static Context app;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase db;
    private DaoMaster.DevOpenHelper mHelper;
    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        RxRetrofitApp.init(this, BuildConfig.DEBUG);
        setDatabase();
    }

    public static MyApplication getInstances(){
        return instances;
    }

    public static Context getAppContext() {
        return app;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
