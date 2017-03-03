package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 3/3/2017.
 */

public class LoginFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {

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
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                break;
        }
    }

    @Override
    public String getActionbarTitle() {
        return "Login";
    }
}
