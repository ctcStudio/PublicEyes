package com.hiepkhach9x.publiceyes.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.menu.CustomSlidingMenu;
import com.hiepkhach9x.publiceyes.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hungh on 3/3/2017.
 */

public class HomeFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler, View.OnClickListener {
    private static final String KEY_PHOTO_PATH = "key.photo.path";
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
        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString(KEY_PHOTO_PATH);
        }

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case WHAT_CHANGE_PHOTO:
                        if (mNavigationManager != null) {
                            ReportFragment reportFragment = ReportFragment.newInstance(mCurrentPhotoPath);
                            mNavigationManager.showPage(reportFragment);
                        }
                        return true;
                    case WHAT_CHANGE_VIDEO:
                        Uri videoPath = (Uri) message.obj;
                        if (mNavigationManager != null) {
                            ReportVideoFragment reportVideoFragment = ReportVideoFragment.newInstance(videoPath);
                            mNavigationManager.showPage(reportVideoFragment);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PHOTO_PATH, mCurrentPhotoPath);
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
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3 * 60);
        startActivityForResult(takeVideoIntent, REQUEST_VIDEO);
    }

    private void photoPickerFile() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PHOTO:
                Message messagePhoto = new Message();
                messagePhoto.what = WHAT_CHANGE_PHOTO;
                mHandler.sendMessage(messagePhoto);
                break;

            case REQUEST_VIDEO:
                Uri mVideoUri = data.getData();
                if (mVideoUri != null) {
                    Message messageVideo = new Message();
                    messageVideo.what = WHAT_CHANGE_VIDEO;
                    messageVideo.obj = mVideoUri;
                    mHandler.sendMessage(messageVideo);
                }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
