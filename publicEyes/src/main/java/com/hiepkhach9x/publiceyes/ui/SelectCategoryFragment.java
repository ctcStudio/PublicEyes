package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 7/1/2017.
 */

public class SelectCategoryFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {

    private static final String ARGS_IMAGE_URL = "args.image.url";
    private static final String ARGS_DESCRIPTION = "args.description";
    private static final String ARGS_IS_VIDEO = "args.is.video";


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_select_category;
    }

    public static SelectCategoryFragment newInstance(String filePath, String description,boolean isVideo) {

        Bundle args = new Bundle();
        args.putString(ARGS_IMAGE_URL, filePath);
        args.putString(ARGS_DESCRIPTION, description);
        args.putBoolean(ARGS_IS_VIDEO, isVideo);
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String imageUrl;
    private String description;
    private boolean isVideo;

    private void parseBundle(Bundle bundle) {
        imageUrl = bundle.getString(ARGS_IMAGE_URL);
        description = bundle.getString(ARGS_DESCRIPTION);
        isVideo = bundle.getBoolean(ARGS_IS_VIDEO);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parseBundle(savedInstanceState);
        } else if (getArguments() != null) {
            parseBundle(getArguments());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARGS_IMAGE_URL, imageUrl);
        outState.putString(ARGS_DESCRIPTION, description);
        outState.putBoolean(ARGS_IS_VIDEO, isVideo);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.category_moto).setOnClickListener(this);
        view.findViewById(R.id.category_oto).setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.category);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_moto:
                mNavigationManager.showPage(CategoryTextFragment.newInstance(CategoryTextFragment.TYPE_MOTO, imageUrl, description, isVideo));
                break;
            case R.id.category_oto:
                mNavigationManager.showPage(CategoryTextFragment.newInstance(CategoryTextFragment.TYPE_CAR, imageUrl, description, isVideo));
                break;
        }
    }
}