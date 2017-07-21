package com.yxy.practicaltool.entity.resulte;

/**
 * Created by yxy on 2017/7/21 0021.
 */

public class CheckCompanyPhoneRes {


    /**
     * ret : 200
     * data : {"ID":91,"CName":"1","Phone":"15093663232","Charge":"2","Address":"3","AddDate":"1899-12-30T00:00:00","OrderBy":10,"Remarks":""}
     * msg : 手机号已存在，请确认是否继续
     */

    public int ret;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * ID : 91
         * CName : 1
         * Phone : 15093663232
         * Charge : 2
         * Address : 3
         * AddDate : 1899-12-30T00:00:00
         * OrderBy : 10
         * Remarks :
         */

        public int ID;
        public String CName;
        public String Phone;
        public String Charge;
        public String Address;
        public String AddDate;
        public int OrderBy;
        public String Remarks;
    }
}
