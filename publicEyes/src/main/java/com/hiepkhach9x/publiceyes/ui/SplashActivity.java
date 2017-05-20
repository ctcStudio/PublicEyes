package com.hiepkhach9x.publiceyes.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.hiepkhach9x.base.BaseAppActivity;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.LoginRequest;
import com.hiepkhach9x.publiceyes.api.response.LoginResponse;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

public class SplashActivity extends BaseAppActivity implements ResponseListener {

    private static final int REQUEST_LOGIN = 100;
    Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        if (UserPref.get().hasActive()) {
            loginByEmail();
            AppPref.get().saveFirstLogin(false);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (UserPref.get().hasActive()) {
                        startMainActivity();
                    } else {
                        startRegisterActivity();
                    }
                }
            }, 1000);
            AppPref.get().saveFirstLogin(true);
        }
    }

    @Override
    public int getContentFrame() {
        return 0;
    }

    private void loginByEmail() {
        String email = UserPref.get().getEmail();
        String password = UserPref.get().getPassword();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        mApi.restartRequest(REQUEST_LOGIN, loginRequest, this);
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

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_LOGIN) {
            return new LoginResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        if (requestId == REQUEST_LOGIN) {
            AppPref.get().saveFirstLogin(false);
            LoginResponse loginResponse = (LoginResponse) response;
            UserPref.get().saveUserInfo(loginResponse.getUser());
            startMainActivity();
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        AppAlertDialog.errorApiAlertDialogOk(this, e, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startRegisterActivity();
            }
        });
    }
}
