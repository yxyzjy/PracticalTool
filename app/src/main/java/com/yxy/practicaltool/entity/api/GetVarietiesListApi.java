package com.yxy.practicaltool.entity.api;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.SPUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class GetVarietiesListApi extends PCBaseApi {

    public String varietiesSearch;

    public GetVarietiesListApi() {
        super();
        setMethod("GetVarietiesList");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.getVarietiesList(SPUtil.getString("random",""), SPUtil.getString("desUserId",""),varietiesSearch);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
