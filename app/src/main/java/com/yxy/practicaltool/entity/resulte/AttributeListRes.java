package com.yxy.practicaltool.entity.resulte;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yxy on 2017/7/17 0017.
 */

public class AttributeListRes implements Serializable {


    /**
     * ret : 200
     * data : [{"ID":27,"Cname":"胸径","OrderBy":100,"ParentId":0},{"ID":28,"Cname":"地径","OrderBy":10,"ParentId":0},{"ID":29,"Cname":"分支点","OrderBy":10,"ParentId":0},{"ID":30,"Cname":"冠幅","OrderBy":10,"ParentId":0},{"ID":31,"Cname":"截杆","OrderBy":10,"ParentId":0},{"ID":88,"Cname":"截杆9-10年生","OrderBy":10,"ParentId":0},{"ID":89,"Cname":"地径","OrderBy":10,"ParentId":0}]
     * msg : 获取产品属性列表成功
     */

    public int ret;
    public String msg;
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        /**
         * ID : 27
         * Cname : 胸径
         * OrderBy : 100
         * ParentId : 0
         */

        public int ID;
        public String Cname;
        public int OrderBy;
        public int ParentId;
        public boolean isSelect;
    }
}
