package com.hiepkhach9x.publiceyes.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.hiepkhach9x.base.AppLog;
import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.LoginRequest;
import com.hiepkhach9x.publiceyes.api.response.LoginResponse;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

import java.util.List;

/**
 * Created by hungh on 3/3/2017.
 */

public class RegisterFragment extends BaseAppFragment implements View.OnClickListener, ResponseListener {

    private  final static String TAG = "RegisterFragment";
    private static final int REQUEST_LOGIN = 100;
    private SimpleFacebook mSimpleFacebook;

    public RegisterFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override
    protected boolean isHasActionbar() {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.sign_up_fb).setOnClickListener(this);
        view.findViewById(R.id.sign_up_email).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_email:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new SignUpFragment());
                }
                break;
            case R.id.sign_up_fb:
                facebookLogin();
                break;
            case R.id.login:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new LoginFragment());
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookLogin() {
        mSimpleFacebook.login(new OnLoginListener() {
            @Override
            public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                Log.d("Facebook","onLogin");
                getFacebookUserInfo();
            }

            @Override
            public void onCancel() {
                Log.d("Facebook","onCancel");
            }

            @Override
            public void onException(Throwable throwable) {
                Log.d("Facebook","onException");
            }

            @Override
            public void onFail(String reason) {
                Log.d("Facebook","onFail");
            }
        });
    }
    private ProgressDialog progressDialog;
    private void getFacebookUserInfo() {
        Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.NAME)
                .add(Profile.Properties.EMAIL)
                .add(Profile.Properties.BIRTHDAY)
                .add(Profile.Properties.GENDER)
                .build();

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
        }
        mSimpleFacebook.getProfile(properties, new OnProfileListener() {
            @Override
            public void onComplete(Profile profile) {
                super.onComplete(profile);
                AppLog.d(TAG, "onComplete");
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (profile != null) {

                    name = profile.getName();
                    email = profile.getEmail();
                    password = profile.getId();
                    loginByEmail();
                }
            }

            @Override
            public void onException(Throwable throwable) {
                AppLog.d(TAG, "onException");
                super.onException(throwable);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFail(String reason) {
                AppLog.d(TAG, "onFail");
                super.onFail(reason);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onThinking() {
                AppLog.d(TAG, "onThinking");
                super.onThinking();
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }
        });
    }
    private String email,password, name, phone, address;
    private void loginByEmail() {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (mNavigationManager != null) {
                mNavigationManager.showPage(SignUpFragment.newInstance(email,password,name, phone,address,false));
            }
            return;
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        showApiLoading();
        mApi.restartRequest(REQUEST_LOGIN,loginRequest,this);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if(requestId == REQUEST_LOGIN) {
            return new LoginResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if(requestId == REQUEST_LOGIN) {
            AppPref.get().saveFirstLogin(true);
            UserPref.get().saveEmail(email);
            UserPref.get().savePassword(password);
            gotoMain();
        }
    }

    private void gotoMain() {
        startActivity(new Intent(getContext(), MainActivity.class));
        if (mNavigationManager != null) {
            mNavigationManager.finishActivity();
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        if (mNavigationManager != null) {
            mNavigationManager.showPage(SignUpFragment.newInstance(email,password,name, phone,address,true));
        }
    }
}
