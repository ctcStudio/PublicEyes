package com.hiepkhach9x.base.api;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by hungh on 4/22/2017.
 */

public abstract class BaseResponse {
    public BaseResponse(Response response) throws IOException, JSONException {
        parseData(response);
    }

    protected abstract void parseData(Response data) throws IOException, JSONException;
}
