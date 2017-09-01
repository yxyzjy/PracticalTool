package com.yxy.practicaltool.entity.resulte;

/**
 * Created by yxy on 2017/7/24 0024.
 */

public class AddCaseRes {


    /**
     * ret : 200
     * data : {"serverFileName":"/upload/201707/24/201707241823272031","serverThumbnailFileName":"/upload/201707/24/small_201707241823272031","sign":"0"}
     * msg : 图片上传成功
     */

    public int ret;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * serverFileName : /upload/201707/24/201707241823272031
         * serverThumbnailFileName : /upload/201707/24/small_201707241823272031
         * sign : 0
         */
        /*public String serverFileName;
        public String serverThumbnailFileName;
        public int sign;*/
    }
}
