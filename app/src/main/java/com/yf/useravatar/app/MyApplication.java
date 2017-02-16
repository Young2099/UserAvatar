package com.yf.useravatar.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ${yf} on 2017/2/16.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //初始化Fresco
        Fresco.initialize(this);
    }

    public static Context getApp() {
        return myApplication;
    }
}
