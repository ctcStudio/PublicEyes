package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.entities.User;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by hungh on 5/17/2017.
 */

public class SignUpResponse extends BaseResponse {
    @SerializedName("Code")
    private int code;
    @SerializedName("Data")
    private User user;

    public SignUpResponse(String response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        SignUpResponse response = gson.fromJson(data,SignUpResponse.class);
        code = response.getCode();
        user = response.getUser();
    }

    public int getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }
}
