package com.hiepkhach9x.publiceyes;

import android.app.Application;

/**
 * Created by hungh on 3/3/2017.
 */

public class App extends Application {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App get() {
        return instance;
    }
}
