package com.hiepkhach9x.base.api;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by hungh on 4/22/2017.
 */

public abstract class BaseResponse {
    public final static int SUCCESS = 0;

    public BaseResponse(String response) throws IOException, JSONException, RuntimeException {
        parseData(response);
    }

    protected abstract void parseData(String data) throws IOException, JSONException;
}
