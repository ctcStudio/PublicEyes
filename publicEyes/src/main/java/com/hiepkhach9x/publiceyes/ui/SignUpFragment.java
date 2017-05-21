package com.hiepkhach9x.publiceyes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Patterns;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.SignUpRequest;
import com.hiepkhach9x.publiceyes.api.response.SignUpResponse;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;

import co.utilities.KeyboardUtils;

public class SignUpFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener,
        ResponseListener {

    private static final int REQUEST_SIGN_UP = 100;

    public SignUpFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_sign_up;
    }


    private UnderLineEditText edEmail, etPassword, edFullName, edPhone, edCmt, edAddress;
    private String email, password, fullName, phone, cmt, address;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.show_pass).setOnClickListener(this);

        edEmail = (UnderLineEditText) view.findViewById(R.id.ed_email);
        etPassword = (UnderLineEditText) view.findViewById(R.id.input_pass);
        edFullName = (UnderLineEditText) view.findViewById(R.id.ed_full_name);
        edPhone = (UnderLineEditText) view.findViewById(R.id.ed_phone);
        edCmt = (UnderLineEditText) view.findViewById(R.id.ed_cmt);
        edAddress = (UnderLineEditText) view.findViewById(R.id.ed_address);
    }

    @Override
    protected boolean isHasActionbar() {
        return true;
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.sign_up);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
            case R.id.btn_continue:
                signUpAccount();
                break;
            case R.id.show_pass:
                etPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                break;
        }
    }

    private void signUpAccount() {
        email = edEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        fullName = edFullName.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
        cmt = edCmt.getText().toString().trim();
        address = edAddress.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(cmt) || TextUtils.isEmpty(address)) {
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(),"",getString(R.string.input_data_error)
                    , true,null);
            alertDialog.show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(),"",getString(R.string.validate_email),true,null);
            alertDialog.show();
            return;
        }

        SignUpRequest request = new SignUpRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setFullName(fullName);
        request.setPhone(phone);
        request.setCmt(cmt);
        request.setAddress(address);
        showApiLoading();
        mApi.restartRequest(REQUEST_SIGN_UP,request,this);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_SIGN_UP) {
            return new SignUpResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        if (requestId == REQUEST_SIGN_UP) {
            AppPref.get().saveFirstLogin(true);
            SignUpResponse signUpResponse = (SignUpResponse) response;
            UserPref.get().saveUserInfo(signUpResponse.getUser());
            UserPref.get().saveEmail(email);
            UserPref.get().savePassword(password);
            startMainActivity();
        }
    }

    private void startMainActivity() {
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
        AlertDialog dialog = AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
