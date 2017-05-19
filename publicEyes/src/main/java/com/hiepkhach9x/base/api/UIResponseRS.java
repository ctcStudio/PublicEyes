package com.hiepkhach9x.base.api;

import android.view.View;

import com.hiepkhach9x.base.AppLog;

import java.lang.ref.SoftReference;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HungHN on 10/20/16.
 * An implementation of {@link ResponseListener}
 * <p>
 * Use existed view to execute {@link ResponseListener(Object)} on UI thread.
 */

public class UIResponseRS implements ResponseListener {

    private SoftReference<View> viewSoftReference;

    private SoftReference<ResponseListener> rsReference;

    public UIResponseRS(View view, ResponseListener responseListener) {
        viewSoftReference = new SoftReference<>(view);
        rsReference = new SoftReference<>(responseListener);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {

        ResponseListener listener = rsReference.get();

        if (listener != null)
            return listener.parse(requestId, response);
        return null;
    }

    @Override
    public void onResponse(final int requestId, final BaseResponse response) {

        View view = viewSoftReference.get();
        final ResponseListener responseListener = rsReference.get();

        if (view == null) {
            AppLog.d("View to post on UI thread is null");
            return;
        }

        if (responseListener == null) {
            AppLog.d("Response Listener is null");
            return;
        }

        view.post(new Runnable() {
            @Override
            public void run() {
                responseListener.onResponse(requestId, response);
            }
        });
    }

    @Override
    public void onError(int requestId, Exception e) {
        ResponseListener listener = rsReference.get();

        if (listener != null)
            listener.onError(requestId, e);
    }
}
