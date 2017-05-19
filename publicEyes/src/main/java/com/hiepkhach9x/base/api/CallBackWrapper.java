package com.hiepkhach9x.base.api;

/**
 * Created by hungh on 4/22/2017.
 */

import android.os.Handler;
import android.os.Looper;

import com.hiepkhach9x.base.api.errors.AuthFailureError;
import com.hiepkhach9x.base.api.errors.BaseError;
import com.hiepkhach9x.base.api.errors.ParserError;
import com.hiepkhach9x.base.api.errors.ServerError;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CallBackWrapper implements Callback {
    private SoftReference<ResponseListener> softReference;
    private int requestId;

    public CallBackWrapper(int requestId, ResponseListener listener) {
        this.requestId = requestId;
        this.softReference = new SoftReference<>(listener);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();

        deliverUIError(e);
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        ResponseListener listener = softReference.get();
        if (listener != null) {

            int statusCode = response.code();

            boolean isError = false;

            if (statusCode < 200 || statusCode > 299) {
                isError = true;
            }
                /*
                If call API successfully
                 */
            if (!isError) {
                try {
                    BaseResponse temp = listener.parse(requestId, response.body().string());
                    response.close();
                    deliverUIResponse(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                    deliverUIError(new ParserError(call, response));
                }

                return;
            }
                /*
                If error occurs
                 */
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED
                    || statusCode == HttpURLConnection.HTTP_FORBIDDEN) {

                deliverUIError(new AuthFailureError(call, response));
            } else {
                deliverUIError(new ServerError(call, response));
            }
        }
    }

    private void deliverUIError(final Exception error) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ResponseListener listener = softReference.get();
                if (listener != null) {
                    listener.onError(requestId, error);
                }
            }
        });
    }

    private void deliverUIResponse(final BaseResponse response) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ResponseListener listener = softReference.get();
                if (listener != null) {
                    listener.onResponse(requestId, response);
                }
            }
        });
    }
}
