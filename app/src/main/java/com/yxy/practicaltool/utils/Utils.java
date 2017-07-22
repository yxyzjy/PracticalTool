package com.yxy.practicaltool.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 是否存在SDCard
     *
     * @return true 存在
     */
    public static boolean checkSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /**
     * 获取SDCard路径
     *
     * @return
     */
    public static String getSDCardPath() {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        return filePath;
    }

    /**
     * 获取文件名称
     *
     * @return
     */
    public static String getImageFileName() {
        return System.currentTimeMillis() + ".png";
    }

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTime = simpleDateFormat.format(date.getTime());
        return currentTime;
    }
}
