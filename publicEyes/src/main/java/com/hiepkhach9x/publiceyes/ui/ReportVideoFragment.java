package com.hiepkhach9x.publiceyes.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/5/2017.
 */

public class ReportVideoFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {

    private static final String TAG = "ReportFragment";
    private static final String ARG_FILE = "ARG.FILE";

    public static ReportVideoFragment newInstance(Uri videoPath) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_FILE, videoPath);
        ReportVideoFragment fragment = new ReportVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Uri mVideoFile;

    @Override
    public String getActionbarTitle() {
        return "Video";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mVideoFile = savedInstanceState.getParcelable(ARG_FILE);
        } else if (getArguments() != null) {
            mVideoFile = getArguments().getParcelable(ARG_FILE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_FILE, mVideoFile);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video_report;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
            case R.id.btn_continue:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new CategoryFragment());
                }
                break;
        }
    }
}
