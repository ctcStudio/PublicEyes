package com.hiepkhach9x.publiceyes.api.response;

import com.hiepkhach9x.base.api.BaseResponse;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by hungh on 5/17/2017.
 */

public class SignUpResponse extends BaseResponse {

    public SignUpResponse(Response response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(Response data) throws IOException, JSONException {

    }
}
