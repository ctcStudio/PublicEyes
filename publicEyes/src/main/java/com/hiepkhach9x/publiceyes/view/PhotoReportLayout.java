package com.hiepkhach9x.publiceyes.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 3/4/2017.
 */

public class PhotoReportLayout extends FrameLayout implements View.OnClickListener {

    private ImageView photo;
    private Bitmap bitmap;
    private PhotoLayoutListener listener;

    public PhotoReportLayout(Context context) {
        super(context);
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
                    listener.onSelectPhoto(bitmap);
                }
                break;
        }
    }

    public void sePhotoLayoutListener(PhotoLayoutListener listener) {
        this.listener = listener;
    }

    public void setPhoto(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (photo != null) {
            photo.setImageBitmap(bitmap);
        }
    }

    public interface PhotoLayoutListener {

        void onRemovePhoto(PhotoReportLayout layout);

        void onSelectPhoto(Bitmap bitmap);
    }
}
