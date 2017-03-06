package com.hiepkhach9x.publiceyes.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.view.PhotoReportLayout;
import com.hiepkhach9x.publiceyes.view.RectangleImageView;

import java.util.ArrayList;

import co.core.imageloader.NDisplayOptions;
import co.mediapicker.NMediaItem;
import co.mediapicker.NMediaOptions;
import co.mediapicker.NMediaPickerActivity;
import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/4/2017.
 */

public class ReportFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler, PhotoReportLayout.PhotoLayoutListener, View.OnClickListener {

    private static final String TAG = "ReportFragment";
    private static final String ARG_FILE = "ARG.FILE";
    private static final int REQUEST_PHOTO = 1234;

    public static ReportFragment newInstance(Uri fileUri) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_FILE, fileUri);
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Uri mFileUri;
    private RectangleImageView mPhotoView;
    private EditText mDescription;
    private LinearLayout mLayoutPhoto;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_report;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(ARG_FILE);
        } else if (getArguments() != null) {
            mFileUri = getArguments().getParcelable(ARG_FILE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_FILE, mFileUri);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = (RectangleImageView) view.findViewById(R.id.photo_view);
        mDescription = (EditText) view.findViewById(R.id.description);
        mLayoutPhoto = (LinearLayout) view.findViewById(R.id.layout_photo);

        view.findViewById(R.id.add_photo).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mFileUri != null) {
            if (mImageLoader != null) {
                NDisplayOptions.Builder builder = new NDisplayOptions.Builder();
                builder.cacheOnDisk(true);
                builder.setImageOnLoading(R.drawable.ic_photo);
                mImageLoader.display(mFileUri, mPhotoView, builder.build());
            }

            PhotoReportLayout photoReportLayout = createPhotoLayout(mFileUri);
            mLayoutPhoto.addView(photoReportLayout);
        }
    }


    private PhotoReportLayout createPhotoLayout(Uri fileUri) {
        PhotoReportLayout reportLayout = new PhotoReportLayout(getContext(), mImageLoader);
        int horMargin = getResources().getDimensionPixelSize(R.dimen.space_normal);
        int size = getResources().getDimensionPixelSize(R.dimen.add_photo_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.leftMargin = horMargin;
        reportLayout.setLayoutParams(layoutParams);
        reportLayout.setPhoto(fileUri);
        reportLayout.sePhotoLayoutListener(this);
        return reportLayout;
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
    public void onRemovePhoto(PhotoReportLayout layout) {
        if (mLayoutPhoto != null)
            mLayoutPhoto.removeView(layout);
    }

    @Override
    public void onSelectPhoto(Uri filePath) {
        if (mImageLoader != null)
            mImageLoader.display(filePath, mPhotoView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
                photoPickerFile();
                break;
            case R.id.layout:
                KeyboardUtils.hideSoftKeyboard(getActivity());
                break;
        }
    }

    private void photoPickerFile() {
        NMediaOptions.Builder builder = new NMediaOptions.Builder();
        NMediaOptions options = builder.setIsCropped(false).setFixAspectRatio(false).selectPhoto().build();
        NMediaPickerActivity.open(this, REQUEST_PHOTO, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PHOTO:
                ArrayList<NMediaItem> mPhotoSelectedList = NMediaPickerActivity.getNMediaItemSelected(data);
                if (mPhotoSelectedList != null) {
                    for (final NMediaItem mediaItem : mPhotoSelectedList) {
                        Uri originalPath = mediaItem.getUriOrigin();
                        if (originalPath != null) {
                            if (mImageLoader != null) {
                                mImageLoader.display(originalPath, mPhotoView);
                            }

                            PhotoReportLayout photoReportLayout = createPhotoLayout(originalPath);
                            if (mLayoutPhoto != null)
                                mLayoutPhoto.addView(photoReportLayout);
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "Error to get media, NULL");
                }

                break;
        }
    }
}
