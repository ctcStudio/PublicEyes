package com.hiepkhach9x.publiceyes;

import android.app.Application;

import com.hiepkhach9x.base.imageloader.UILImageLoader;

import co.core.imageloader.NImageLoader;

/**
 * Created by hungh on 3/3/2017.
 */

public class App extends Application {

    public static App instance;

    NImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mImageLoader = new UILImageLoader(this);
    }

    public static App get() {
        return instance;
    }

    public NImageLoader getImageLoader() {
        return mImageLoader;
    }
}
