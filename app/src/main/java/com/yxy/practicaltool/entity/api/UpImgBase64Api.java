package com.yxy.practicaltool.entity.api;

import com.yxy.practicaltool.entity.HttpService;
import com.yxy.practicaltool.entity.request.LoadImgReq;
import com.yxy.practicaltool.utils.SPUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class UpImgBase64Api extends PCBaseApi {

    public String txtFileName, sign;
    public LoadImgReq loadImgReq;

    public UpImgBase64Api() {
        super();
        setMethod("UpImgBase64");
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService service = retrofit.create(HttpService.class);
        loadImgReq = new LoadImgReq();
        loadImgReq.random = SPUtil.getString("random", "");
        loadImgReq.desUserId = SPUtil.getString("desUserId", "");
        loadImgReq.txtFileName = txtFileName;
        loadImgReq.sign = sign;
        return service.upImgBase64(SPUtil.getString("random",""),
                SPUtil.getString("desUserId",""),txtFileName,sign);
//        return service.upImgBase64(loadImgReq);
    }

    @Override
    public Object call(Object o) {
        return null;
    }
}
