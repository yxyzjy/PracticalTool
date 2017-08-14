package com.yxy.practicaltool.entity.api.caseapi;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.Utils;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class AddCaseApi extends CaseBaseApi {

    public String title, info;

    public AddCaseApi() {
        super();
        setMethod("Add_Case");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        String data = Utils.getCurrentDay();
        return service.addCase("cn", Utils.md5(data), "1", title, info);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
