package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.User;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by HungHN on 5/18/17.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("Data")
    private User user;
    public LoginResponse(String response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        LoginResponse response = gson.fromJson(data,LoginResponse.class);
        code = response.getCode();
        user = response.getUser();
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }
}
