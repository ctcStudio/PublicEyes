package com.hiepkhach9x.base.toolbox;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HungHN on 3/4/2017.
 */

public class PermissionGrant {
    public static final String TAG = "PermissionGrant";

    /**
     *
     * @param activity this activity request permission
     * @param PERMISSIONS array permission request
     * @param PERMISSIONS_REQUEST_ID Request code
     * @return true if request permission.
     */
    public static boolean verify(Activity activity, final String[] PERMISSIONS, final int PERMISSIONS_REQUEST_ID) {
        if (underAPI23())
            return true;

        if (activity == null) {
            return false;
        }

        boolean shouldShowRequest = false;
        List<String> pendingPermission = new ArrayList<>();

        for (int i = 0; i < PERMISSIONS.length; i++) {
            int check = ContextCompat.checkSelfPermission(activity, PERMISSIONS[i]);
            if (check != PackageManager.PERMISSION_GRANTED) {
                pendingPermission.add(PERMISSIONS[i]);
                if (shouldShowRequest == false) {
                    boolean should = ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSIONS[i]);
                    if (should)
                        shouldShowRequest = true;
                }
            }
        }

        int denyPermissionLength = pendingPermission.size();
        final String[] denyPermission = new String[denyPermissionLength];
        for (int i = 0; i < denyPermissionLength; i++) {
            denyPermission[i] = pendingPermission.get(i);
        }

        if (denyPermissionLength > 0) {
            activity.requestPermissions(denyPermission, PERMISSIONS_REQUEST_ID);
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param fragment this fragment request permission
     * @param PERMISSIONS array permission request
     * @param PERMISSIONS_REQUEST_ID Request code
     * @return true if request permission.
     */
    public static boolean verify(Fragment fragment, final String[] PERMISSIONS, final int PERMISSIONS_REQUEST_ID) {
        if (underAPI23())
            return true;

        if (fragment == null) {
            return false;
        }

        boolean shouldShowRequest = false;
        List<String> pendingPermission = new ArrayList<>();

        for (int i = 0; i < PERMISSIONS.length; i++) {
            int check = ContextCompat.checkSelfPermission(fragment.getContext(), PERMISSIONS[i]);
            if (check != PackageManager.PERMISSION_GRANTED) {
                pendingPermission.add(PERMISSIONS[i]);
                if (shouldShowRequest == false) {
                    boolean should = fragment.shouldShowRequestPermissionRationale(PERMISSIONS[i]);
                    if (should)
                        shouldShowRequest = true;
                }
            }
        }

        int denyPermissionLength = pendingPermission.size();
        final String[] denyPermission = new String[denyPermissionLength];
        for (int i = 0; i < denyPermissionLength; i++) {
            denyPermission[i] = pendingPermission.get(i);
        }

        if (denyPermissionLength > 0) {
            fragment.requestPermissions(denyPermission, PERMISSIONS_REQUEST_ID);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkSelfPermission(Context context, final String[] PERMISSIONS) {
        if (underAPI23())
            return true;
        if (context == null) {
            return false;
        }
        boolean grantedPermission = true;

        for (int i = 0; i < PERMISSIONS.length; i++) {
            int check = ContextCompat.checkSelfPermission(context, PERMISSIONS[i]);
            if (check != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return grantedPermission;
    }

    public static boolean underAPI23() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }
}
