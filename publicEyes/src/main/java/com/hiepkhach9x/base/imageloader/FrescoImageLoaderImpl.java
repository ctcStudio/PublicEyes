package com.hiepkhach9x.base.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import co.core.imageloader.NDisplayOptions;
import co.core.imageloader.NImageLoader;
import co.core.imageloader.NImageLoadingListener;
import co.core.imageloader.NImageLoadingProgressListener;

/**
 * @author ToanTK
 */

/**
 * <a href="http://frescolib.org/">Fresco<a/>
 * library impls {@link NImageLoader} for default ImageLoader
 */
public class FrescoImageLoaderImpl implements NImageLoader {

    private Context mContext;

    public FrescoImageLoaderImpl(Context context) {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(context, config);
        mContext = context;
    }

    @Override
    public void display(String url, ImageView imageView) {
        display(url, imageView, null, null, null);
    }

    @Override
    public void display(final String url, ImageView imageView,
                        final NImageLoadingListener listener) {
        display(url, imageView, null, listener, null);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(url, imageView, null, null, progressListener);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(url, imageView, null, listener, progressListener);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions displayOption) {
        display(url, imageView, displayOption, null, null);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener) {
        display(url, imageView, options, listener, null);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(url, imageView, options, null, progressListener);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {

        if (imageView == null || !(imageView instanceof SimpleDraweeView)) return;

        if (options != null) {

            GenericDraweeHierarchy oldHierarchy = ((SimpleDraweeView) imageView).getHierarchy();
            oldHierarchy.setFadeDuration(300);

            if (!options.isNoImageWhileLoading() && options.getImageOnLoading() != -1)
                oldHierarchy.setPlaceholderImage(options.getImageOnLoading());

            if (options.getImageOnFail() != -1) {
                oldHierarchy.setFailureImage(options.getImageOnFail());
            }

            ((SimpleDraweeView) imageView).setHierarchy(oldHierarchy);
        }

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(true)
                .build();

        ((SimpleDraweeView) imageView).setController(controller);
    }

    @Override
    public void display(Uri uri, ImageView imageView) {
        display(uri, imageView, null, null, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NDisplayOptions displayOption) {
        display(uri, imageView, displayOption, null, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NImageLoadingListener listener) {
        display(uri, imageView, null, listener, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(uri, imageView, null, null, progressListener);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(uri, imageView, null, listener, progressListener);
    }

    @Override
    public void display(Uri uri, ImageView imageView, NDisplayOptions options,
                        NImageLoadingListener listener) {
        display(uri, imageView, options, listener, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView, NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(uri, imageView, options, null, progressListener);
    }

    @Override
    public void display(Uri uri, ImageView imageView, NDisplayOptions options,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(uri.toString(), imageView, options, listener, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView) {
        display(resourceId, imageView, null, null, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NImageLoadingListener listener) {
        display(resourceId, imageView, null, listener, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(resourceId, imageView, null, null, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options) {
        display(resourceId, imageView, options, null, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener) {
        display(resourceId, imageView, options, listener, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(resourceId, imageView, options, null, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(resourceId, imageView, null, listener, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display("drawable://" + resourceId, imageView, options, listener,
                progressListener);
    }

    @Override
    public void display(File file, ImageView imageView) {
        display(file, imageView, null, null, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(file, imageView, null, null, progressListener);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NImageLoadingListener listener) {
        display(file, imageView, null, listener, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(file, imageView, null, listener, progressListener);
    }

    @Override
    public void display(File file, ImageView imageView, NDisplayOptions options) {
        display(file, imageView, options, null, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(file, imageView, options, null, progressListener);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener) {
        display(file, imageView, options, listener, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(Uri.fromFile(file), imageView, options, listener,
                progressListener);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener) {
        loadImage(url, listener, null);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener,
                          int widthSize, int heightSize) {
        loadImage(url, listener, null, widthSize, heightSize);
    }

    @Override
    public void loadImage(String url,
                          NImageLoadingProgressListener progressListener) {
        loadImage(url, null, progressListener);
    }

    @Override
    public void loadImage(String url,
                          NImageLoadingProgressListener progressListener, int widthSize,
                          int heightSize) {
        loadImage(url, null, progressListener, widthSize, heightSize);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener,
                          NImageLoadingProgressListener progressListener) {
        LoadingAndProgressListenerImpl impl = new LoadingAndProgressListenerImpl(
                listener, progressListener);
        //TODO try to use listeners
        ImageRequest request = ImageRequest.fromUri(url);
        Fresco.getImagePipeline().prefetchToDiskCache(request, mContext);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener,
                          NImageLoadingProgressListener progressListener, int widthSize,
                          int heightSize) {
        LoadingAndProgressListenerImpl impl = new LoadingAndProgressListenerImpl(
                listener, progressListener);

        //TODO try to use listeners
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(new ResizeOptions(widthSize, heightSize))
                .build();
        Fresco.getImagePipeline().prefetchToDiskCache(request, mContext);
    }

    private static class LoadingAndProgressListenerImpl {
        NImageLoadingListener loadingListener;
        NImageLoadingProgressListener progressListener;

        public LoadingAndProgressListenerImpl(
                NImageLoadingListener loadingListener) {
            this.loadingListener = loadingListener;
        }

        public LoadingAndProgressListenerImpl(
                NImageLoadingProgressListener progressListener) {
            this.progressListener = progressListener;
        }

        public LoadingAndProgressListenerImpl(
                NImageLoadingListener loadingListener,
                NImageLoadingProgressListener progressListener) {
            this.loadingListener = loadingListener;
            this.progressListener = progressListener;
        }
    }
}
