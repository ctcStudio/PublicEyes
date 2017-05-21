package com.hiepkhach9x.publiceyes.ui;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.UploadFileRequest;
import com.hiepkhach9x.publiceyes.api.response.UploadFileResponse;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

import java.io.File;

import co.utilities.KeyboardUtils;
import co.utilities.PhotoUtils;
import co.utilities.StorageUtils;
import co.utilities.Utils;

/**
 * Created by hungh on 3/5/2017.
 */

public class ReportVideoFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener,
        ResponseListener {

    private static final String TAG = "ReportFragment";
    private static final String ARG_FILE = "ARG.FILE";

    private static final int REQUEST_UPLOAD_VIDEO = 106;

    public static ReportVideoFragment newInstance(Uri videoPath) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_FILE, videoPath);
        ReportVideoFragment fragment = new ReportVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Uri mVideoFile;
    private EditText mDescription;

    @Override
    public String getActionbarTitle() {
        return getString(R.string.video_report);
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
        mDescription = (EditText) view.findViewById(R.id.description);
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
                uploadFile();
                break;
        }
    }

    private void uploadFile() {
        String videoPath = PhotoUtils.getRealPathFromURI(getContext().getContentResolver(),mVideoFile);
        UploadFileRequest uploadFileRequest = new UploadFileRequest();
        if(!TextUtils.isEmpty(videoPath)) {
            File file = new File(videoPath);
            uploadFileRequest.setFile(file);
        }
        showApiLoading();
        mApi.restartRequest(REQUEST_UPLOAD_VIDEO, uploadFileRequest, this);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_UPLOAD_VIDEO) {
            return new UploadFileResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if (requestId == REQUEST_UPLOAD_VIDEO) {
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
        AlertDialog dialog = AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
