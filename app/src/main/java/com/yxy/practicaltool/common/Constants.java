package com.yxy.practicaltool.common;

/**
 * Created by yxy on 2017/7/6 0006.
 */

public class Constants {

    /**
     * 是否打印请求报文&返回报文(抓包使用，默认开启)
     */
    public static final boolean IS_LOG_BODY = true;

    public static final String TAG = "yxy";

    public static final String BASE_URL = "http://qm.xinwangmm.com/Webservice.asmx/";

    public static int CAMERA_REQUEST=18;//拍照权限

    public static final String DATA_DIR = "/practicalTool/";

    //上传资源
    public static final String  UpImgBase64= "http://122.114.102.73:8090/WebService.asmx/UpImgBase64";
    //发布案例
    public static final String  UpImgBase64Post= "http://admin.xinwangmm.com/WebService_CASE.asmx/UpImgBase64Post";
}
