package com.yxy.practicaltool.entity;


import com.yxy.practicaltool.bean.EmptyBean;
import com.yxy.practicaltool.entity.api.CheckCompanyPhoneApi;
import com.yxy.practicaltool.entity.request.LoadImgReq;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CaseTypeRes;
import com.yxy.practicaltool.entity.resulte.CheckCompanyPhoneRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.entity.resulte.LoginRes;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public interface HttpService {

    @GET("Login")
    Observable<LoginRes> login(@Query("txtUserName") String username, @Query("txtUserPwd") String password, @Query("random") String random);

    @GET("GetCompanyList")
    Observable<CompanyListRes> getCompanyList(@Query("random") String random, @Query("desUserId") String desUserId, @Query("companySearch") String companySearch);

    @GET("GetVarietiesList")
    Observable<CompanyListRes> getVarietiesList(@Query("random") String random, @Query("desUserId") String desUserId, @Query("varietiesSearch") String varietiesSearch);


    @GET("GetAttributeList")
    Observable<AttributeListRes> getAttributeList(@Query("random") String random, @Query("desUserId") String desUserId, @Query("parentId") String parentId);


    @GET("CheckCompanyPhone")
    Observable<CheckCompanyPhoneRes> checkCompanyPhone(@Query("random") String random, @Query("desUserId") String desUserId, @Query("phone") String phone);


    @GET("AddCompany")
    Observable<AttributeListRes> addCompany(@Query("random") String random, @Query("desUserId") String desUserId, @Query("phone") String phone, @Query("name") String name, @Query("charge") String charge, @Query("address") String address);

    @POST("/UpImgBase64")
    Observable<AttributeListRes> upImgBase64(@Query("random") String random, @Query("desUserId") String desUserId,
                                             @Query("txtFileName") String phone, @Query("sign") String name);

    @POST("UpImgBase64Post")
    Observable<AttributeListRes> upImgBase64(@Body LoadImgReq loadImgReq);

//    @GET("UpImgBase64")
//    Observable<CheckCompanyPhoneApi> upImgBase64(@Query("random") String random, @Query("desUserId") String desUserId, @Query("txtFileName") String txtFileName, @Query("sign") String sign);


    @GET("Get_Case")
    Observable<CaseTypeRes> getCase(@Query("_lan") String _lan, @Query("secret_key") String secret_key, @Query("key_type") String key_type);




    @GET("AddProduct")
    Observable<AttributeListRes> addProduct(@Query("random") String random, @Query("desUserId") String desUserId, @Query("CName") String CName,
                                            @Query("Vid") int Vid, @Query("Cid") int Cid, @Query("H_State") int H_State,
                                            @Query("Num") String Num, @Query("Describe") String Describe, @Query("Remarks") String Remarks,
                                            @Query("ProductAttr") String ProductAttr, @Query("PhotoDetail") String PhotoDetail);


}
