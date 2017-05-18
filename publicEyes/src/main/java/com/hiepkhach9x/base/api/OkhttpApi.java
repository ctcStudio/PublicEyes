package com.hiepkhach9x.base.api;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by hungh on 5/17/2017.
 */

public class OkHttpApi implements Api {


    private final OkHttpClient mOkHttpClient;

    public OkHttpApi(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    @Override
    public void startRequest(int requestId, BaseRequest request, ResponseListener responseListener) {
        callApi(requestId, request, responseListener);
    }

    @Override
    public void restartRequest(int requestId, BaseRequest request, ResponseListener responseListener) {
        cancelCallWithTag(requestId);
        callApi(requestId, request, responseListener);
    }


    private void callApi(int requestId, BaseRequest baseRequest, ResponseListener listener) {
        Request.Builder request = new Request.Builder()
                .url(baseRequest.getUrl())
                .method(baseRequest.getMethod(), baseRequest.getBody());
        Headers headers = baseRequest.getHeaders();
        if (headers != null) {
            request.headers(headers);
        }
        request.tag(requestId);
        mOkHttpClient.newCall(request.build()).enqueue(new CallBackWrapper(requestId, listener));
    }

    private void cancelCallWithTag(int requestId) {
        // A call may transition from queue -> running. Remove queued Calls first.
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if ((int)call.request().tag() == requestId)
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if ((int)call.request().tag() == requestId)
                call.cancel();
        }
    }
}
