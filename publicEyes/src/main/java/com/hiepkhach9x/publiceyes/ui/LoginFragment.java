package com.hiepkhach9x.publiceyes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/3/2017.
 */

public class LoginFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {

    private UnderLineEditText etPassword;

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

        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.show_pass).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                AppPref.get().saveFirstLogin(true);
                startActivity(new Intent(getContext(), MainActivity.class));
                if (mNavigationManager != null) {
                    mNavigationManager.finishActivity();
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
            case R.id.show_pass:
                etPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                break;
        }
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.login);
    }
}
