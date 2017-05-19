package com.hiepkhach9x.publiceyes.api;

import android.os.Handler;
import android.os.Message;

import com.hiepkhach9x.base.api.Api;
import com.hiepkhach9x.base.api.BaseRequest;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.base.api.errors.ParserError;
import com.hiepkhach9x.publiceyes.api.request.SignUpRequest;
import com.hiepkhach9x.publiceyes.store.DummyData;

import java.text.ParseException;

/**
 * Created by HungHN on 5/19/17.
 */

public class FakeImpl implements Api {
    @Override
    public void startRequest(int requestId, BaseRequest request, ResponseListener responseListener) {

    }

    @Override
    public void restartRequest(int requestId, BaseRequest request, ResponseListener responseListener) {

    }

    private void callApi(int requestId, BaseRequest request, ResponseListener responseListener) {
        try {
            BaseResponse response = null;
            if (request instanceof SignUpRequest) {
                response = responseListener.parse(requestId, DummyData.SIGN_UP_RESPONSE);
            }
            responseListener.onResponse(requestId, response);
        } catch (Exception e) {
            e.printStackTrace();
            responseListener.onError(requestId, new ParserError(null,null));
        }
    }
}
