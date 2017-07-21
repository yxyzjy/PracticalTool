package com.yxy.practicaltool.entity.api;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.SPUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class CheckCompanyPhoneApi extends PCBaseApi {

    public String phone;

    public CheckCompanyPhoneApi() {
        super();
        setMethod("CheckCompanyPhone");
    }



    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.checkCompanyPhone(SPUtil.getString("random",""), SPUtil.getString("desUserId",""),phone);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
