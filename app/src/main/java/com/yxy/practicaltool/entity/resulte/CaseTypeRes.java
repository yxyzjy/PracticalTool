package com.yxy.practicaltool.entity.resulte;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yxy on 2017/7/25 0025.
 */

public class CaseTypeRes implements Serializable{


    /**
     * ret : 200
     * data : [{"Id":215,"Title":"施工案例"},{"Id":222,"Title":"发货案例"},{"Id":264,"Title":"发货案例2"},{"Id":265,"Title":"测试发货案例weservice接口"},{"Id":266,"Title":"口令测试"},{"Id":267,"Title":"123ches"}]
     * msg : 获取成功!
     */

    public int ret;
    public String msg;
    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * Id : 215
         * Title : 施工案例
         */

        public int Id;
        public String Title;
        public boolean isSelect;
    }
}
