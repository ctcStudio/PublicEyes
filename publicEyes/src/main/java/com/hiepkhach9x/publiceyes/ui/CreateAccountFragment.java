package com.hiepkhach9x.publiceyes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;

import co.utilities.KeyboardUtils;

public class CreateAccountFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {

    public CreateAccountFragment() {
    }

    private UnderLineEditText etPassword;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_create_account;
    }

    @Override
    protected boolean isHasActionbar() {
        return true;
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.create_account);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.show_pass).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);

        etPassword = (UnderLineEditText) view.findViewById(R.id.input_pass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                startActivity(new Intent(getContext(), MainActivity.class));
                if (mNavigationManager != null) {
                    mNavigationManager.finishActivity();
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.show_pass:
                etPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                break;
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
        }
    }
}
