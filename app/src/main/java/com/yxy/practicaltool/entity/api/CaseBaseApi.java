package com.yxy.practicaltool.entity.api;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.common.Constants;

/**
 * Created by yxy on 2017/7/5 0005.
 */

public abstract class CaseBaseApi extends BaseApi {
    public CaseBaseApi() {
        setBaseUrl(Constants.BASE_URL_CASE);
        setShowProgress(true);
        setCancel(true);
        setConnectionTime(60);
    }
}
