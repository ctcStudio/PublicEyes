package com.hiepkhach9x.publiceyes.store;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.publiceyes.App;
import com.hiepkhach9x.publiceyes.entities.User;

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
    private final String KEY_ID_CARD = "key.id.card";
    private final String KEY_POINT = "key.point";
    private final String KEY_AGE = "key.age";
    private final String KEY_ADDRESS = "key.address";


    public static UserPref get() {
        return new UserPref(App.get());
    }

    public UserPref(Context context) {
        super(context);
    }

    public void saveUserInfo(User user) {
        saveEmail(user.getEmail());
        saveFullName(user.getFullName());
        saveMobileNumber(user.getMobileNumber());
        saveIdCard(user.getIdCard());
        savePoint(user.getPoint());
        saveAge(user.getAge());
        saveAddress(user.getAddress());
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

    public boolean saveIdCard(String idCard) {
        return mEditor.putString(KEY_ID_CARD, idCard).commit();
    }

    public String getIdCard() {
        return getSharedPreference().getString(KEY_ID_CARD, "");
    }

    public boolean savePoint(int point) {
        return mEditor.putInt(KEY_POINT, point).commit();
    }

    public int getPoint() {
        return getSharedPreference().getInt(KEY_POINT, 0);
    }

    public boolean saveAge(int age) {
        return mEditor.putInt(KEY_AGE, age).commit();
    }

    public int getAge() {
        return getSharedPreference().getInt(KEY_AGE, 0);
    }

    public boolean saveAddress(String address) {
        return mEditor.putString(KEY_ADDRESS, address).commit();
    }

    public String getAddress() {
        return getSharedPreference().getString(KEY_ADDRESS, "");
    }

    public boolean hasActive() {
        return (!TextUtils.isEmpty(getEmail()) && !TextUtils.isEmpty(getPassword()));
    }

    @Override
    public void clear() {
        mEditor.clear().commit();
    }
}

