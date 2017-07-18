package com.yxy.practicaltool.entity.api;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.utils.SPUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class GetAttributeListApi extends PCBaseApi {

    public String parentId;

    public GetAttributeListApi(String parentId) {
        super();
        this.parentId = parentId;
        setMethod("GetAttributeList"+parentId);
    }



    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        return service.getAttributeList(SPUtil.getString("random",""), SPUtil.getString("desUserId",""),parentId);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
