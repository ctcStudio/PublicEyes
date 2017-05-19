package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hungh on 3/3/2017.
 */

public class RegisterFragment extends BaseAppFragment implements View.OnClickListener, ResponseListener {

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
                Toast.makeText(getContext(), R.string.developing, Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new LoginFragment());
                }
                break;
        }
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {

    }

    @Override
    public void onError(int requestId, Exception e) {

    }
}
