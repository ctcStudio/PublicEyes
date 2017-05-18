package com.hiepkhach9x.base;

import android.util.Log;

/**
 * Created by freesky1102 on 7/10/15.
 */
public class AppLog {

    public static final boolean isEnabled = true;

    private static final String TAG = "My App";

    public static void d(String tag, String message) {
        if (!isEnabled) return;
        if (message == null || tag == null) return;
        Log.d(tag, message);
    }

    public static void d(String message) {
        if (!isEnabled) return;
        if (message == null) return;
        Log.d(TAG, message);
    }

    public static void e(String message) {
        if (!isEnabled) return;
        if (message == null) return;
        Log.e(TAG, message);
    }

    public static void i(String message) {
        if (!isEnabled) return;
        if (message == null) return;
        Log.i(TAG, message);
    }

    public static void i(String tag, String message) {
        if (!isEnabled) return;
        if (message == null || tag == null) return;
        Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        if (!isEnabled) return;
        if (message == null || tag == null) return;
        Log.e(tag, message);
    }

    public static void w(String tag, String message) {
        if (!isEnabled) return;
        if (message == null || tag == null) return;
        Log.w(tag, message);
    }

    public static void w(String message) {
        if (!isEnabled) return;
        if (message == null) return;
        Log.w(TAG, message);
    }
}
