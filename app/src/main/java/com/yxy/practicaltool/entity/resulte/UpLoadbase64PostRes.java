package com.yxy.practicaltool.entity.resulte;

import java.util.List;

/**
 * Created by yxy on 2017/8/8 0008.
 */

public class UpLoadbase64PostRes {


    /**
     * ret : 200
     * data : [{"ImgUrl":" /uploadfiles/news/170808201708081753147656_thumbs.jpg"}]
     * msg : 登录成功
     */

    public int ret;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * ImgUrl :  /uploadfiles/news/170808201708081753147656_thumbs.jpg
         */

        public String ImgUrl;
    }
}
