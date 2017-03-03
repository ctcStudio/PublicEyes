package com.hiepkhach9x.publiceyes.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.store.UserPref;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserPref.get().hasActive()) {
                    startMainActivity();
                } else {
                    startRegisterActivity();
                }
            }
        }, 2000);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void startRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
