package com.hiepkhach9x.publiceyes.ui;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import org.json.JSONException;
import org.json.JSONObject;

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

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    private boolean isLoginFacebook;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etPassword = (UnderLineEditText) view.findViewById(R.id.input_pass);
        etEmail = (UnderLineEditText) view.findViewById(R.id.ed_email);

        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.show_pass).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
        fbLoginButton = (LoginButton) view.findViewById(R.id.login_facebook);
        fbLoginButton.setFragment(this);
        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                Log.d("facebook", "success");
                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());
                                try {
                                    if (object.has("email")) {
                                        email = object.getString("email");
                                    }
                                    if (object.has("id")) {
                                        password = object.getString("id");
                                    }

                                    isLoginFacebook = true;
                                    loginByEmail();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,hometown");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                Log.d("facebook", "cancel");
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
                Log.d("facebook", "error");
            }
        });
        String[] readPermsArr = {"basic_info", "email", "user_about_me", "user_activities",
                "user_mobile_phone", "user_hometown", "user_location"};
        fbLoginButton.clearPermissions();
        fbLoginButton.setReadPermissions(readPermsArr);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                isLoginFacebook = false;
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
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            String message;
            if (isLoginFacebook) {
                message = getString(R.string.input_fb_error);
            } else {
                message = getString(R.string.input_data_error);
            }
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(), "", message, true, null);
            alertDialog.show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            String message;
            if (isLoginFacebook) {
                message = getString(R.string.input_fb_error);
            } else {
                message = getString(R.string.validate_email);
            }
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(), "", message, true, null);
            alertDialog.show();
            return;
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        showApiLoading();
        mApi.restartRequest(REQUEST_LOGIN, loginRequest, this);
        LoginManager.getInstance().logOut();
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.login);
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
        dismissApiLoading();
        if (requestId == REQUEST_LOGIN) {
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
