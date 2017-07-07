package com.yxy.practicaltool.entity.api;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.Utils;
import com.yxy.practicaltool.entity.HttpService;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class LoginApi extends PCBaseApi {

    private String username;
    private String password;

    public LoginApi() {
        super();
        setMethod("WebService.asmx/Login");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.login(username,password, Utils.getRandom());
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
