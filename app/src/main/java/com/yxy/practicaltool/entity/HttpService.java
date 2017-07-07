package com.yxy.practicaltool.entity;


import com.yxy.practicaltool.entity.resulte.LoginRes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public interface HttpService {

    @GET("WebService.asmx/Login")
    Observable<LoginRes> login(@Query("txtUserName") String username, @Query("txtUserPwd") String password, @Query("random")String random);
}
