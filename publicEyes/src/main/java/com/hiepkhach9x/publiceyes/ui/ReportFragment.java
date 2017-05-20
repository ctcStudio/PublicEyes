package com.hiepkhach9x.publiceyes.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.ImageUtil;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.UploadFileRequest;
import com.hiepkhach9x.publiceyes.api.response.LoginResponse;
import com.hiepkhach9x.publiceyes.api.response.UploadFileResponse;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;
import com.hiepkhach9x.publiceyes.view.RectangleImageView;

import java.io.File;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/4/2017.
 */

public class ReportFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler, View.OnClickListener, ResponseListener {

    private static final String TAG = "ReportFragment";
    private static final String ARG_FILE = "ARG.FILE";
    private static final int REQUEST_PHOTO = 1234;
    private static final int REQUEST_UPLOAD_PHOTO = 103;

    public static ReportFragment newInstance(String photoPath) {

        Bundle args = new Bundle();
        args.putString(ARG_FILE, photoPath);
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String mPhotoPath = "";
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
        Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", new File(mPhotoPath));
        ImageUtil.loadImage(getContext(),photoURI, mPhotoView);

        view.findViewById(R.id.layout).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void uploadFile() {
        File file = new File(mPhotoPath);
        UploadFileRequest uploadFileRequest = new UploadFileRequest();
        uploadFileRequest.setFile(file);
        mApi.restartRequest(REQUEST_UPLOAD_PHOTO, uploadFileRequest, this);
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
        uploadFile();
        return true;
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

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_UPLOAD_PHOTO) {
            return new UploadFileResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if (requestId == REQUEST_UPLOAD_PHOTO) {
            UploadFileResponse uploadFileResponse = (UploadFileResponse) response;
            if (uploadFileResponse.isSuccess()) {
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(CategoryFragment.newInstance(uploadFileResponse.getMsg().getPath(),
                            mDescription.getText().toString().trim()));
                }
            }
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
    }
}
