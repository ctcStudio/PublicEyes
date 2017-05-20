package com.hiepkhach9x.base.api;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by hungh on 4/22/2017.
 */

public abstract class BaseResponse {

    @SerializedName("Code")
    protected int code;

    public BaseResponse(String response) throws IOException, JSONException, RuntimeException {
        parseData(response);
    }

    protected abstract void parseData(String data) throws IOException, JSONException;

    public abstract boolean isSuccess();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
