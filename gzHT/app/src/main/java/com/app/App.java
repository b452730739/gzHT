package com.app;

import android.app.Application;

import com.elegps.help.ACache;

public class App extends Application {

    public  static ACache ACACHE;

    @Override
    public void onCreate() {
        super.onCreate();
        ACACHE =   ACache.getContext(getApplicationContext());

    }
}
