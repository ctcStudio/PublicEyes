package com.hiepkhach9x.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.hiepkhach9x.publiceyes.R;

import java.io.File;


public class ImageUtil {
    public static void loadImage(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).asBitmap().placeholder(R.drawable.ic_photo).into(imageView);
    }

    public static void loadImage(Context context, Uri uri, ImageView imageView, int size) {
        Glide.with(context).load(uri).asBitmap().placeholder(R.drawable.ic_photo).override(size, size).into(imageView);
    }

    public static void loadImage(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).asBitmap().placeholder(R.drawable.ic_photo).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).asBitmap().placeholder(R.drawable.ic_photo).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide.with(context).load(url).asBitmap().override(width, height).centerCrop().placeholder(R.drawable.ic_photo).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int size) {
        loadImage(context, url, imageView, size, size);
    }

    public static void loadRadiusImage(final Context context, String url, final int radius, ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(R.drawable.ic_photo).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(radius);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void loadRoundedImage(Context context, String url, ImageView imageView) {
        loadRadiusImage(context, url, 20, imageView);
    }


    public static void loadCircleImage(final Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop()
                .placeholder(R.drawable.ic_photo).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void loadBannerImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).asBitmap().placeholder(R.drawable.bg_dummy_banner).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
}