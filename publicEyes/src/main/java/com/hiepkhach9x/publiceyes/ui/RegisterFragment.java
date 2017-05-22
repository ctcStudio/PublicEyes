package com.hiepkhach9x.publiceyes.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

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
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.LoginRequest;
import com.hiepkhach9x.publiceyes.api.response.LoginResponse;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by hungh on 3/3/2017.
 */

public class RegisterFragment extends BaseAppFragment implements View.OnClickListener, ResponseListener {

    private final static String TAG = "RegisterFragment";
    private static final int REQUEST_LOGIN = 100;

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.sign_up_email).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);

        fbLoginButton = (LoginButton) view.findViewById(R.id.sign_up_fb);
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
                                    if (object.has("name")) {
                                        name = object.getString("name");
                                    }
                                    if (object.has("id")) {
                                        password = object.getString("id");
                                    }
                                    if (object.has("location")) {
                                        JSONObject locationObj = object.getJSONObject("location");
                                        address = locationObj.getString("name");
                                    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_email:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new SignUpFragment());
                }
                break;
            case R.id.login:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new LoginFragment());
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String email, password, name, phone, address;

    private void loginByEmail() {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(), "", getString(R.string.input_fb_error), true, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mNavigationManager != null) {
                        mNavigationManager.showPage(SignUpFragment.newInstance(email, password, name, phone, address, false));
                    }
                }
            });
            alertDialog.setCanceledOnTouchOutside(false);
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
            mNavigationManager.showPage(SignUpFragment.newInstance(email, password, name, phone, address, true));
        }
    }
}
