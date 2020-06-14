package com.study.classapp;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static MyApp myApplication;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static MyApp getMyApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        context = getApplicationContext();
        initData();
    }


    private void initData() {

    }
}
