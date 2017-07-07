package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

/**
 * Created by yxy on 2017/7/7 0007.
 */

public class Utils {

    public static String getRandom(){
        String strRand="" ;
        for(int i=0;i<4;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }
}
