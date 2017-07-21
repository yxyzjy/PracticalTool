package com.yxy.practicaltool.entity;


import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.entity.resulte.LoginRes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public interface HttpService {

    @GET("Login")
    Observable<LoginRes> login(@Query("txtUserName") String username, @Query("txtUserPwd") String password, @Query("random")String random);

    @GET("GetCompanyList")
    Observable<CompanyListRes> getCompanyList(@Query("random") String random, @Query("desUserId") String desUserId, @Query("companySearch") String companySearch);

    @GET("GetVarietiesList")
    Observable<CompanyListRes> getVarietiesList(@Query("random") String random, @Query("desUserId") String desUserId, @Query("varietiesSearch") String varietiesSearch);


    @GET("GetAttributeList")
    Observable<AttributeListRes> getAttributeList(@Query("random") String random, @Query("desUserId") String desUserId, @Query("parentId") String parentId);


    @GET("CheckCompanyPhone")
    Observable<AttributeListRes> checkCompanyPhone(@Query("random") String random, @Query("desUserId") String desUserId, @Query("phone") String phone);




}
