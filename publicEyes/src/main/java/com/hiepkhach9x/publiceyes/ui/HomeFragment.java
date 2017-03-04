package com.hiepkhach9x.publiceyes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.menu.CustomSlidingMenu;
import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 3/3/2017.
 */

public class HomeFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler, View.OnClickListener {
    private CustomSlidingMenu slidingMenu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomSlidingMenu) {
            slidingMenu = (CustomSlidingMenu) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (slidingMenu != null) {
            slidingMenu.setEnableSliding(true);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_top;
    }

    @Override
    protected boolean isHasActionbar() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.choose_photo).setOnClickListener(this);
        view.findViewById(R.id.choose_video).setOnClickListener(this);
    }

    @Override
    public boolean onLeftHandled() {
        if (slidingMenu != null) {
            slidingMenu.toggle();
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightHandled() {
        return false;
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_photo:
            case R.id.choose_video:
                if(mNavigationManager !=null) {
                    mNavigationManager.showPage(new ReportFragment());
                }
        }
    }
}
