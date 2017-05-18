package com.hiepkhach9x.publiceyes.api.response;

import com.hiepkhach9x.base.api.BaseResponse;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by HungHN on 5/18/17.
 */

public class GetUserResponse extends BaseResponse {
    public GetUserResponse(Response response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(Response data) throws IOException, JSONException {

    }
}
