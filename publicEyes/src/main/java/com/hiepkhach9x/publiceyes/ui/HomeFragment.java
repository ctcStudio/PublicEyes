package com.hiepkhach9x.publiceyes.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.menu.CustomSlidingMenu;
import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.R;

import java.util.ArrayList;

import co.mediapicker.NMediaItem;
import co.mediapicker.NMediaOptions;
import co.mediapicker.NMediaPickerActivity;
import co.utilities.VideoUtils;

/**
 * Created by hungh on 3/3/2017.
 */

public class HomeFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler, View.OnClickListener {
    private final String TAG = "HomeFragment";
    private final int REQUEST_PHOTO = 1234;
    private final int REQUEST_VIDEO = 4321;

    private final int WHAT_CHANGE_PHOTO = 1;
    private final int WHAT_CHANGE_VIDEO = 2;

    private CustomSlidingMenu slidingMenu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomSlidingMenu) {
            slidingMenu = (CustomSlidingMenu) context;
        }
    }

    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case WHAT_CHANGE_PHOTO:
                        Uri imgPath = (Uri) message.obj;
                        if (mNavigationManager != null) {
                            ReportFragment reportFragment = ReportFragment.newInstance(imgPath);
                            mNavigationManager.showPage(reportFragment);
                        }
                        return true;
                    case WHAT_CHANGE_VIDEO:
                        Uri videoPath = (Uri) message.obj;
                        if (mNavigationManager != null) {
                            ReportFragment reportFragment = ReportFragment.newInstance(videoPath);
                            mNavigationManager.showPage(reportFragment);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (slidingMenu != null) {
            slidingMenu.setEnableSliding(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (slidingMenu != null) {
            slidingMenu.setEnableSliding(false);
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
                photoPickerFile();
                break;
            case R.id.choose_video:
                videoPickerFile();
                break;
        }
    }

    private void videoPickerFile() {
        NMediaOptions.Builder builder = new NMediaOptions.Builder();
        NMediaOptions options = builder.setMaxVideoDuration(Config.TIME_LIMIT_VIDEO * 1000).setShowWarningBeforeRecordVideo(true).selectVideo().build();
        NMediaPickerActivity.open(this, REQUEST_VIDEO, options);
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

                        Message message = new Message();
                        message.what = WHAT_CHANGE_PHOTO;
                        message.obj = originalPath;
                        mHandler.sendMessage(message);
                        break;
                    }
                } else {
                    Log.e(TAG, "Error to get media, NULL");
                }

                break;
            case REQUEST_VIDEO:
                ArrayList<NMediaItem> mVideoSelectedList = NMediaPickerActivity
                        .getNMediaItemSelected(data);
                if (mVideoSelectedList != null) {
                    for (NMediaItem mediaItem : mVideoSelectedList) {
                        Uri videoUri = mediaItem.getUriOrigin();
                        long duration = VideoUtils.getDuration(getContext(), videoUri);

                        if (duration <= 0 || duration >= (Config.TIME_LIMIT_VIDEO + 1) * 1000) {
                            Log.d(TAG,"Duration is: " + duration);
                        } else {
                            Message message = new Message();
                            message.what = WHAT_CHANGE_PHOTO;
                            message.obj = videoUri;
                            mHandler.sendMessage(message);
                        }
                        break;
                    }
                } else {
                    Log.e(TAG, "Error to get media, NULL");
                }
                break;
        }
    }
}
