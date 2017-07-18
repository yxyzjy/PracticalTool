package com.yxy.practicaltool.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yxy on 2017/7/7 0007.
 */

public class Utils {

    public static String getRandom() {
        String strRand = "";
        for (int i = 0; i < 4; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        return strRand;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
