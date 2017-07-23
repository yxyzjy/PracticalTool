package com.yxy.practicaltool.entity.api.caseapi;

import com.yxy.practicaltool.entity.HttpService;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class AddCaseApi extends CaseBaseApi {

    public String _lan="cn",secret_key="5D5D5ED10FF810510E79E25FF4C82867";

    public AddCaseApi() {
        super();
        setMethod("Get_Case");
    }



    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.getCase(_lan,secret_key);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
