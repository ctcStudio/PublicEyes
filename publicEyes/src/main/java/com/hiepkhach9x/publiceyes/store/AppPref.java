package com.hiepkhach9x.publiceyes.store;

import android.content.Context;
import android.text.TextUtils;

import com.hiepkhach9x.publiceyes.App;

import co.utilities.BasePrefers;

/**
 * Created by hungh on 3/3/2017.
 */

public class AppPref extends BasePrefers {

    private final String USER_PREFERENCE = "APP.PREFERENCE";

    private final String KEY_FIRST_LOGIN = "key.first.login";
    public static final String KEY_IS_SHOW_NEWS = "is.show.news";
    public static final String KEY_DATE_SHOW_NEW = "date.show.new";

    public static AppPref get() {
        return new AppPref(App.get());
    }

    public AppPref(Context context) {
        super(context);
    }

    @Override
    protected String getFileNamePrefers() {
        return USER_PREFERENCE;
    }

    public boolean saveFirstLogin(boolean firstLogin) {
        return mEditor.putBoolean(KEY_FIRST_LOGIN, firstLogin).commit();
    }

    public boolean isFirstLogin() {
        return getSharedPreference().getBoolean(KEY_FIRST_LOGIN, true);
    }


    public void saveIsShowNews(boolean show) {
        mEditor.putBoolean(KEY_IS_SHOW_NEWS, show).commit();
    }

    public boolean isShowNews() {
        return getSharedPreference().getBoolean(KEY_IS_SHOW_NEWS, true);
    }

    public void saveDateShowNews(long time) {
        mEditor.putLong(KEY_DATE_SHOW_NEW, time).commit();
    }

    public long getDateShowNews() {
        return getSharedPreference().getLong(KEY_DATE_SHOW_NEW, 0);
    }

    @Override
    public void clear() {
        mEditor.clear().commit();
    }
}

