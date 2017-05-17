package com.hiepkhach9x.publiceyes;

import android.app.Application;

import com.hiepkhach9x.base.api.Api;
import com.hiepkhach9x.base.api.OkHttpApi;
import com.hiepkhach9x.base.imageloader.UILImageLoader;

import co.core.imageloader.NImageLoader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by hungh on 3/3/2017.
 */

public class App extends Application {

    public static App instance;

    NImageLoader mImageLoader;
    Api mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mImageLoader = new UILImageLoader(this);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        mApi = new OkHttpApi(okHttpClient);

    }

    public static App get() {
        return instance;
    }

    public NImageLoader getImageLoader() {
        return mImageLoader;
    }

    public Api getApi() {
        return mApi;
    }
}
