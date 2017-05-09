package com.hiepkhach9x.publiceyes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.view.RectangleImageView;

import java.io.File;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/4/2017.
 */

public class ReportFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler, View.OnClickListener {

    private static final String TAG = "ReportFragment";
    private static final String ARG_FILE = "ARG.FILE";
    private static final int REQUEST_PHOTO = 1234;

    public static ReportFragment newInstance(String photoPath) {

        Bundle args = new Bundle();
        args.putString(ARG_FILE, photoPath);
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String mPhotoPath;
    private RectangleImageView mPhotoView;
    private EditText mDescription;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_report;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoPath = savedInstanceState.getString(ARG_FILE);
        } else if (getArguments() != null) {
            mPhotoPath = getArguments().getString(ARG_FILE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_FILE, mPhotoPath);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = (RectangleImageView) view.findViewById(R.id.photo_view);
        mDescription = (EditText) view.findViewById(R.id.description);

        view.findViewById(R.id.layout).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        File file = new File(mPhotoPath);
        mImageLoader.display(file,mPhotoView);
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
        return getString(R.string.report_violation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
        }
    }
}
