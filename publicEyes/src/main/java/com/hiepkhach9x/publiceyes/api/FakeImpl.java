package com.hiepkhach9x.publiceyes.api;

import com.hiepkhach9x.base.api.Api;
import com.hiepkhach9x.base.api.BaseRequest;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.base.api.errors.ParserError;
import com.hiepkhach9x.publiceyes.api.request.GetCampaignsRequest;
import com.hiepkhach9x.publiceyes.api.request.GetListCategoryRequest;
import com.hiepkhach9x.publiceyes.api.request.GetListReportRequest;
import com.hiepkhach9x.publiceyes.api.request.GetUserRequest;
import com.hiepkhach9x.publiceyes.api.request.LoginRequest;
import com.hiepkhach9x.publiceyes.api.request.SignUpRequest;
import com.hiepkhach9x.publiceyes.api.request.UpdateReportRequest;
import com.hiepkhach9x.publiceyes.api.request.UploadFileRequest;
import com.hiepkhach9x.publiceyes.store.DummyData;

/**
 * Created by HungHN on 5/19/17.
 */

public class FakeImpl implements Api {
    @Override
    public void startRequest(int requestId, BaseRequest request, ResponseListener responseListener) {
        callApi(requestId,request,responseListener);
    }

    @Override
    public void restartRequest(int requestId, BaseRequest request, ResponseListener responseListener) {
        callApi(requestId,request,responseListener);
    }

    private void callApi(int requestId, BaseRequest request, ResponseListener responseListener) {
        try {
            BaseResponse response = null;
            if (request instanceof SignUpRequest) {
                response = responseListener.parse(requestId, DummyData.SIGN_UP_RESPONSE);
            } else if (request instanceof LoginRequest) {
                response = responseListener.parse(requestId, DummyData.LOGIN_RESPONSE);
            } else if (request instanceof UploadFileRequest) {
                response = responseListener.parse(requestId, DummyData.UPLOAD_RESPONSE);
            } else if (request instanceof UpdateReportRequest) {
                response = responseListener.parse(requestId, DummyData.MESSAGE_RESPONSE);
            } else if (request instanceof GetUserRequest) {
                response = responseListener.parse(requestId, DummyData.GET_USER_RESPONSE);
            } else if (request instanceof GetListReportRequest) {
                response = responseListener.parse(requestId, DummyData.GET_LIST_VIOLATION);
            } else if (request instanceof GetCampaignsRequest) {
                response = responseListener.parse(requestId, DummyData.GET_OPERATON_LIST);
            } else if (request instanceof GetListCategoryRequest) {
                response = responseListener.parse(requestId, DummyData.GET_CATEGORY_LIST);
            } else {
                response = responseListener.parse(requestId, DummyData.MESSAGE_RESPONSE);
            }
            responseListener.onResponse(requestId, response);
        } catch (Exception e) {
            e.printStackTrace();
            responseListener.onError(requestId, new ParserError(null,null));
        }
    }
}
