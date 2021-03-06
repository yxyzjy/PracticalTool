package com.yxy.practicaltool.entity.api;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.SPUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class GetCompanyListApi extends PCBaseApi {

    public String companySearch;

    public GetCompanyListApi() {
        super();
        setMethod("GetCompanyList");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.getCompanyList(SPUtil.getString("random",""), SPUtil.getString("desUserId",""),companySearch);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
