package com.example.coolweather;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;


/**
 * Created by nizhenyang on 2017/9/21.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
