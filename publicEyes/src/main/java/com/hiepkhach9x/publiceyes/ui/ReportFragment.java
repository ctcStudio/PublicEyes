package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 3/4/2017.
 */

public class ReportFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_report;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onLeftHandled() {
        if (mNavigationManager != null) {
            mNavigationManager.goBack();
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightHandled() {
        if (mNavigationManager != null) {
            mNavigationManager.showPage(new CategoryFragment());
            return true;
        }
        return false;
    }

    @Override
    public String getActionbarTitle() {
        return "Report Violation";
    }
}
