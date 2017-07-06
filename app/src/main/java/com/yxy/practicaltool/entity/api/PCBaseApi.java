package com.yxy.practicaltool.entity.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/5 0005.
 */

public abstract class PCBaseApi extends BaseApi {
    public PCBaseApi() {
        setShowProgress(true);
        setCancel(true);
        setConnectionTime(60);
    }
}
