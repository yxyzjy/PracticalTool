package com.yxy.practicaltool.entity.api.caseapi;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.entity.api.PCBaseApi;
import com.yxy.practicaltool.utils.SPUtil;
import com.yxy.practicaltool.utils.Utils;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class GetCaseApi extends CaseBaseApi {

    public String _lan="cn",secret_key;

    public GetCaseApi() {
        super();
        setMethod("Get_Case");
    }



    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        String data = Utils.getCurrentDay();
        return service.getCase(_lan,Utils.md5(data),"1");
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
