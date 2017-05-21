package com.hiepkhach9x.publiceyes.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.hiepkhach9x.base.BaseAppActivity;
import com.hiepkhach9x.base.toolbox.PermissionGrant;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.R;

public class RegisterActivity extends BaseAppActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (mNavigationManager != null
                && mNavigationManager.getActivePage() == null) {
            mNavigationManager.swapPage(new RegisterFragment());
        }

        PermissionGrant.verify(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_PERMISSION_LOCATION);
    }

    @Override
    public int getContentFrame() {
        return R.id.content_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mNavigationManager != null && mNavigationManager.getActivePage() != null) {
            mNavigationManager.getActivePage().onActivityResult(requestCode, resultCode, data);
        }
    }
}
