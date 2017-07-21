package com.yxy.practicaltool.entity.api;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.SPUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class AddCompanyApi extends PCBaseApi {

    public String phone,name,charge,address;

    public AddCompanyApi() {
        super();
        setMethod("AddCompany");
    }



    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.addCompany(SPUtil.getString("random",""), SPUtil.getString("desUserId",""),phone,name,charge,address);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
