package com.hiepkhach9x.publiceyes;

import android.app.Application;

import com.hiepkhach9x.base.api.Api;
import com.hiepkhach9x.base.api.OkHttpImpl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by hungh on 3/3/2017.
 */

public class App extends Application {

    public static App instance;

    Api mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        mApi = new OkHttpImpl(okHttpClient);
        //mApi = new FakeImpl();
    }

    public static App get() {
        return instance;
    }

    public Api getApi() {
        return mApi;
    }
}
