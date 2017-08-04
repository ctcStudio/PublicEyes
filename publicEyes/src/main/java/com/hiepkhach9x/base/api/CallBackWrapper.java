package com.hiepkhach9x.base.api;

/**
 * Created by hungh on 4/22/2017.
 */

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;

import com.hiepkhach9x.base.AppLog;
import com.hiepkhach9x.base.api.errors.AuthFailureError;
import com.hiepkhach9x.base.api.errors.ParserError;
import com.hiepkhach9x.base.api.errors.ServerError;
import com.hiepkhach9x.publiceyes.App;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.api.ResponseCode;

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


            String responseBody = response.body().string();
            AppLog.d("Api",responseBody);
            if (!isError) {
                try {
                    BaseResponse temp = listener.parse(requestId, responseBody);
                    if (temp.isSuccess()) {
                        deliverUIResponse(temp);
                    } else {
                        if (temp.getCode() == ResponseCode.UNAUTHORIZED) {
                            pushBroadcastError(temp.getCode());
                        }
                        deliverUIError(new ServerError(call,responseBody));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    deliverUIError(new ParserError(call, responseBody));
                }

                return;
            }
                /*
                If error occurs
                 */
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED
                    || statusCode == HttpURLConnection.HTTP_FORBIDDEN) {

                deliverUIError(new AuthFailureError(call, responseBody));
            } else {
                deliverUIError(new ServerError(call,responseBody));
            }
            response.close();
        }
    }

    private void pushBroadcastError(int code) {
        Intent intent = new Intent(Constants.EXTRA_FILTER_ERROR_REQUEST);
        intent.putExtra(Constants.EXTRA_CODE, code);
        LocalBroadcastManager.getInstance(App.get()).sendBroadcast(intent);
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
