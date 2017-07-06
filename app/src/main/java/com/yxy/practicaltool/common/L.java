package com.yxy.practicaltool.common;

import android.util.Log;

/**
 * Created by yxy on 2017/7/6 0006.
 */

public class L {

    public static void e(String content){
        Log.e(Constants.TAG,content);
    }
    public static void e(String  tag ,String content){
        Log.e(tag,content);
    }
}
