package com.yxy.practicaltool.entity.api;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;
import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.SPUtil;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class AddProductApi extends PCBaseApi {

    public String CName, Num, Describe, Remarks,PhotoDetail;
    public int Vid, Cid, H_State;
    public List<String> ProductAttr, RemarkArray, FocusPhoto;

    public AddProductApi() {
        super();
        setMethod("AddProduct");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.addProduct(SPUtil.getString("random", ""), SPUtil.getString("desUserId", ""),
                CName, Vid, Cid, H_State, Num, Describe, Remarks, ProductAttr, PhotoDetail, RemarkArray, FocusPhoto);
    }

    @Override
    public Object call(BaseResultEntity o) {
        return null;
    }
}
