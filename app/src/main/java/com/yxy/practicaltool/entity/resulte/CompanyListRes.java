package com.yxy.practicaltool.entity.resulte;

import java.util.List;

/**
 * Created by yxy on 2017/7/17 0017.
 */

public class CompanyListRes {

    /**
     * ret : 200
     * data : [{"ID":91,"CName":"1","Phone":"15093663232"},{"ID":92,"CName":"2","Phone":"15093663232"},{"ID":86,"CName":"甘霖苗木1","Phone":"15038117578"},{"ID":87,"CName":"甘霖苗木2","Phone":"15038117578"},{"ID":90,"CName":"新旺5测试","Phone":"15038117578"},{"ID":89,"CName":"新旺苗木1","Phone":"15038117578"},{"ID":88,"CName":"新旺苗木2","Phone":"15038117578"}]
     * msg : 获取单位列表成功
     */

    public int ret;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * ID : 91
         * CName : 1
         * Phone : 15093663232
         */

        public int ID;
        public String CName;
        public String Phone;
        public boolean isSelect;
    }
}
