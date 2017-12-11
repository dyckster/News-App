package com.dyckster.newsapp;

import android.app.Application;

public class NewsApplication extends Application {

    private static NewsApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static NewsApplication getInstance() {
        return instance;
    }
}
