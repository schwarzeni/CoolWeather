package com.example.coolweather.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by nizhenyang on 2017/9/21.
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     *  生成请求服务器的API
     */

    public static String getAreaInfoAPI(String ...upLevelCode) {

        String getAreaInfoAPI = "http://guolin.tech/api/";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getAreaInfoAPI).append("china/");

        if (upLevelCode.length != 0) {
            for(String tmp : upLevelCode) {
                stringBuilder.append(tmp).append("/");
            }
        }
        return stringBuilder.toString();
    }
}
