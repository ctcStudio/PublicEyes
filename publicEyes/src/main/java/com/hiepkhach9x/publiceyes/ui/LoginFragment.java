package com.hiepkhach9x.publiceyes.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Patterns;
import android.view.View;

import com.hiepkhach9x.base.AppLog;
import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.LoginRequest;
import com.hiepkhach9x.publiceyes.api.response.LoginResponse;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;


import java.util.List;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/3/2017.
 */

public class LoginFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener, ResponseListener {
    private static final String TAG = "LoginFragment";
    private static final int REQUEST_LOGIN = 100;

    private UnderLineEditText etPassword;
    private UnderLineEditText etEmail;
    private String email;
    private String password;

    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected boolean isHasActionbar() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etPassword = (UnderLineEditText) view.findViewById(R.id.input_pass);
        etEmail = (UnderLineEditText) view.findViewById(R.id.ed_email);

        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.show_pass).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
        view.findViewById(R.id.login_facebook).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_facebook:
                gotoMain();
                break;
            case R.id.btn_continue:
                loginByEmail();
                break;
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
            case R.id.show_pass:
                etPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                break;
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

    private void loginByEmail() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(),"",getString(R.string.input_data_error),true,null);
            alertDialog.show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(),"",getString(R.string.validate_email),true,null);
            alertDialog.show();
            return;
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        showApiLoading();
        mApi.restartRequest(REQUEST_LOGIN,loginRequest,this);
    }

    private void facebookLogin() {
        Permission[] permissions = new Permission[] {
                Permission.USER_ABOUT_ME,
                Permission.PUBLIC_PROFILE,
                Permission.USER_PHOTOS,
                Permission.EMAIL,
                Permission.PUBLISH_ACTION,
                Permission.USER_BIRTHDAY
        };
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getString(R.string.app_facebook_id))
                .setPermissions(permissions)
                .build();
        SimpleFacebook simpleFacebook = SimpleFacebook.getInstance(getActivity());
        simpleFacebook.setConfiguration(configuration);

        simpleFacebook.login(new OnLoginListener() {
            @Override
            public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                getFacebookUserInfo();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onException(Throwable throwable) {

            }

            @Override
            public void onFail(String reason) {

            }
        });
    }

    private void getFacebookUserInfo() {

        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
        pictureAttributes.setHeight(360);
        pictureAttributes.setWidth(360);
        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

        Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.NAME)
                .add(Profile.Properties.EMAIL)
                .add(Profile.Properties.BIRTHDAY)
                .add(Profile.Properties.GENDER)
                .add(Profile.Properties.PICTURE, pictureAttributes)
                .build();

        SimpleFacebook simpleFacebook = SimpleFacebook.getInstance(getActivity());

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
        }
        simpleFacebook.getProfile(properties, new OnProfileListener() {
            @Override
            public void onComplete(Profile profile) {
                super.onComplete(profile);
                AppLog.d(TAG, "onComplete");
                if (profile != null) {

                    String gender = profile.getGender();
                    String name = profile.getName();
                    String avatar = profile.getPicture();
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

    @Override
    public String getActionbarTitle() {
        return getString(R.string.login);
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

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        AlertDialog dialog = AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
