package com.example.huangwanping.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by leafact on 2016/12/21.
 */

public class MyApplication extends Application {
    public  static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
