package com.yxy.practicaltool.entity.resulte;

/**
 * Created by yxy on 2017/7/27 0027.
 */

public class Uploadbase64Res {


    /**
     * ret : 200
     * data : {"serverFileName":"/upload/201707/27/201707271703414843","serverThumbnailFileName":"/upload/201707/27/small_201707271703414843","sign":"0"}
     * msg : 图片上传成功
     */

    public int ret;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * serverFileName : /upload/201707/27/201707271703414843
         * serverThumbnailFileName : /upload/201707/27/small_201707271703414843
         * sign : 0
         */

        public String serverFileName;
        public String serverThumbnailFileName;
        public int sign;
    }
}
