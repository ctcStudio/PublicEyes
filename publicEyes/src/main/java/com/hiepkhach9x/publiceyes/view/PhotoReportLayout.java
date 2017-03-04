package com.hiepkhach9x.publiceyes.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hiepkhach9x.publiceyes.R;

import co.core.imageloader.NImageLoader;

/**
 * Created by hungh on 3/4/2017.
 */

public class PhotoReportLayout extends FrameLayout implements View.OnClickListener {

    private ImageView photo;
    private Uri filePath;
    private PhotoLayoutListener listener;
    private NImageLoader mImageLoader;

    public PhotoReportLayout(Context context, NImageLoader imageLoader) {
        super(context);
        mImageLoader = imageLoader;
        initView();
    }

    public PhotoReportLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PhotoReportLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_photo, this);

        photo = (ImageView) findViewById(R.id.photo);
        findViewById(R.id.remove).setOnClickListener(this);
        photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remove:
                if (listener != null) {
                    listener.onRemovePhoto(this);
                }
                break;
            case R.id.photo:
                if (listener != null) {
                    listener.onSelectPhoto(filePath);
                }
                break;
        }
    }

    public void setImageLoader(NImageLoader imageLoader) {
        this.mImageLoader = imageLoader;
    }

    public void sePhotoLayoutListener(PhotoLayoutListener listener) {
        this.listener = listener;
    }

    public void setPhoto(Uri filePath) {
        this.filePath = filePath;
        if (photo != null && mImageLoader != null) {
            mImageLoader.display(this.filePath, photo);
        }
    }

    public interface PhotoLayoutListener {

        void onRemovePhoto(PhotoReportLayout layout);

        void onSelectPhoto(Uri filePath);
    }
}
