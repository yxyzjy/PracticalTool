package com.yxy.practicaltool.entity.resulte;

import java.util.List;

/**
 * Created by yxy on 2017/8/8 0008.
 */

public class UpLoadbase64PostRes {


    /**
     * ret : 200
     * data : [{"ImgUrl":"/uploadfiles/news/170810201708101813532500_thumbs.jpg"}]
     * msg : 图片上传成功!
     */

    public String ret;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * ImgUrl : /uploadfiles/news/170810201708101813532500_thumbs.jpg
         */

        public String ImgUrl;
    }
}
