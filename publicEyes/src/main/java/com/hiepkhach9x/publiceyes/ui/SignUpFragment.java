package com.hiepkhach9x.publiceyes.ui;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;

public class SignUpFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {

    public SignUpFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
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
            case R.id.btn_continue:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new CreateAccountFragment());
                }
                break;
        }
    }
}
