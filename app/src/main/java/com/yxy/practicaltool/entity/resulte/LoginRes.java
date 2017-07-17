package com.yxy.practicaltool.entity.resulte;

/**
 * Created by yxy on 2017/7/6 0006.
 */

public class LoginRes {

    /**
     * ret : 200
     * data : {"real_name":"超管","desUserId":"DC568643ED3F94E8"}
     * msg : 登录成功
     */

    public int ret;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * real_name : 超管
         * desUserId : DC568643ED3F94E8
         */

        public String real_name;
        public String desUserId;
    }
}
