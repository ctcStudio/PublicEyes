package co.mediapicker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import co.core.fragments.NFragment;
import co.utilities.PhotoUtils;
import co.utilities.StorageUtils;

/**
 * @author TUNGDX
 */

/**
 * For crop photo. Only crop one item at same time.
 */
public class NPhotoCropFragment extends NFragment implements OnClickListener {
    private static final String EXTRA_MEDIA_SELECTED = "extra_media_selected";
    private static final String EXTRA_MEDIA_OPTIONS = "extra_media_options";

    private NCropListener mCropListener;
    private NMediaOptions mMediaOptions;
    private NMediaItem mMediaItemSelected;
    private CropImageView mCropImageView;
    private View mRotateLeft, mRotateRight;
    private View mCancel;
    private View mSave;
    private ProgressDialog mDialog;

    public static NPhotoCropFragment newInstance(NMediaItem item,
                                                 NMediaOptions options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MEDIA_SELECTED, item);
        bundle.putParcelable(EXTRA_MEDIA_OPTIONS, options);
        NPhotoCropFragment cropFragment = new NPhotoCropFragment();
        cropFragment.setArguments(bundle);
        return cropFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCropListener = (NCropListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMediaItemSelected = savedInstanceState
                    .getParcelable(EXTRA_MEDIA_SELECTED);
            mMediaOptions = savedInstanceState
                    .getParcelable(EXTRA_MEDIA_OPTIONS);
        } else {
            Bundle bundle = getArguments();
            mMediaItemSelected = bundle.getParcelable(EXTRA_MEDIA_SELECTED);
            mMediaOptions = bundle.getParcelable(EXTRA_MEDIA_OPTIONS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MEDIA_OPTIONS, mMediaOptions);
        outState.putParcelable(EXTRA_MEDIA_SELECTED, mMediaItemSelected);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mediapicker_crop,
                container, false);
        init(root);
        return root;
    }

    private void init(View view) {
        mCropImageView = (CropImageView) view.findViewById(R.id.crop);
        mRotateLeft = view.findViewById(R.id.rotate_left);
        mRotateRight = view.findViewById(R.id.rotate_right);
        mCancel = view.findViewById(R.id.cancel);
        mSave = view.findViewById(R.id.save);

        mRotateLeft.setOnClickListener(this);
        mRotateRight.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        mCropImageView.setCropShape(CropImageView.CropShape.OVAL);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCropImageView.setFixedAspectRatio(mMediaOptions.isFixAspectRatio());
        mCropImageView.setAspectRatio(mMediaOptions.getAspectX(),
                mMediaOptions.getAspectY());
        String filePath = null;
        String scheme = mMediaItemSelected.getUriOrigin().getScheme();
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            filePath = PhotoUtils.getRealPathFromURI(getActivity()
                    .getContentResolver(), mMediaItemSelected.getUriOrigin());
        } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
            filePath = mMediaItemSelected.getUriOrigin().getPath();
        }
        if (TextUtils.isEmpty(filePath)) {
            Log.e("PhotoCrop", "not found file path");
            getFragmentManager().popBackStack();
            return;
        }
        int width = getResources().getDisplayMetrics().widthPixels / 3 * 2;
        Bitmap bitmap = PhotoUtils.decodeSampledBitmapFromFile(filePath, width,
                width);

        int degree = PhotoUtils.getAngle(filePath);
        mCropImageView.setImageBitmap(bitmap);
        mCropImageView.rotateImage(degree);

    }


    @Override
    public void onClick(View v) {

        if (v == mRotateLeft) {
            // must catch exception, maybe bitmap in CropImage null
            try {
                mCropImageView.rotateImage(-90);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == mRotateRight) {
            try {
                mCropImageView.rotateImage(90);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == mCancel) {
            getFragmentManager().popBackStack();
        } else if (v == mSave) {

            /**
             * change cropper lib
             * update from crop in AsyncTask to UI Thread
             */
            Bitmap bitmap = mCropImageView.getCroppedImage();
            Uri uri = saveBitmapCropped(bitmap);
            mMediaItemSelected.setUriCropped(uri);
            mCropListener.onSuccess(mMediaItemSelected);
        }
    }

    private Uri saveBitmapCropped(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        try {
            File file;
            if (mMediaOptions.getCroppedFile() != null) {
                file = mMediaOptions.getCroppedFile();
            } else {
                file = StorageUtils.createTempFile(getActivity());
            }
            boolean success = bitmap.compress(CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
            if (success) {
                return Uri.fromFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCropImageView = null;
        mDialog = null;
        mSave = null;
        mCancel = null;
        mRotateLeft = null;
        mRotateRight = null;
    }
}
