package com.yxy.practicaltool.entity.api.caseapi;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;
import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.entity.api.PCBaseApi;
import com.yxy.practicaltool.utils.SPUtil;
import com.yxy.practicaltool.utils.Utils;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class AddShipmentCaseApi extends CaseBaseApi {

    public String title, image, content, Seo_key,Seo_Description,ProductAttr;
    public int id, Cid, H_State;

    public AddShipmentCaseApi() {
        super();
        setMethod("Add_ShipmentCase");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        String data = Utils.getCurrentDay();
        return service.addShipmentCase("cn",Utils.md5(data),
                id, title, image,  content, Seo_key, Seo_Description, "1");
    }

    @Override
    public Object call(BaseResultEntity o) {
        return null;
    }
}
