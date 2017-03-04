package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/4/2017.
 */

public class LocationFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {
    @Override
    public String getActionbarTitle() {
        return "Set Location";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_location;
    }

    private ImageView ivGetLocation;
    private UnderLineEditText etYourLocation;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivGetLocation = (ImageView) view.findViewById(R.id.get_location);
        etYourLocation = (UnderLineEditText) view.findViewById(R.id.input_location);

        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                if (mNavigationManager != null) {
                    mNavigationManager.replaceAll(new HomeFragment());
                }
                break;
            case R.id.layout:
                if (isAdded()) {
                    KeyboardUtils.hideSoftKeyboard(getActivity());
                }
                break;
        }
    }
}
