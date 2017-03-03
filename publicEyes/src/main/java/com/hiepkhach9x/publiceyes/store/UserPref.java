package com.hiepkhach9x.publiceyes.store;

import android.content.Context;
import android.text.TextUtils;

import com.hiepkhach9x.publiceyes.App;

import co.utilities.BasePrefers;

/**
 * Created by hungh on 3/3/2017.
 */

public class UserPref extends BasePrefers {

    private final String USER_PREFERENCE = "USER.PREFERENCE";

    private final String KEY_EMAIL = "key.email";
    private final String KEY_PASSWORD = "key.password";
    private final String KEY_FULL_NAME = "key.full.name";
    private final String KEY_MOBILE_NUMBER = "key.mobile.number";

    public static UserPref get() {
        return new UserPref(App.get());
    }

    public UserPref(Context context) {
        super(context);
    }

    @Override
    protected String getFileNamePrefers() {
        return USER_PREFERENCE;
    }

    public boolean saveEmail(String email) {
        return mEditor.putString(KEY_EMAIL, email).commit();
    }

    public String getEmail() {
        return getSharedPreference().getString(KEY_EMAIL, "");
    }

    public boolean savePassword(String password) {
        return mEditor.putString(KEY_PASSWORD, password).commit();
    }

    public String getPassword() {
        return getSharedPreference().getString(KEY_PASSWORD, "");
    }

    public boolean saveFullName(String fullName) {
        return mEditor.putString(KEY_FULL_NAME, fullName).commit();
    }

    public String getFullName() {
        return getSharedPreference().getString(KEY_FULL_NAME, "");
    }

    public boolean saveMobileNumber(String mobile) {
        return mEditor.putString(KEY_MOBILE_NUMBER, mobile).commit();
    }

    public String getMobileNumber() {
        return getSharedPreference().getString(KEY_MOBILE_NUMBER, "");
    }

    public boolean hasActive() {
        return (!TextUtils.isEmpty(getEmail()) && !TextUtils.isEmpty(getPassword()));
    }
}

