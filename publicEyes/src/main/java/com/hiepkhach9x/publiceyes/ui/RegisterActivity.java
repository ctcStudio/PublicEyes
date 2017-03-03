package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;

import com.hiepkhach9x.base.BaseAppActivity;
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
    }

    @Override
    public int getContentFrame() {
        return R.id.content_layout;
    }
}
