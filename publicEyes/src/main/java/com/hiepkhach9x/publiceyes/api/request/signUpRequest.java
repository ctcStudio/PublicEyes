package com.hiepkhach9x.publiceyes.api.request;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.Api;
import com.hiepkhach9x.base.api.BaseRequest;
import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.api.ApiConfig;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by hungh on 5/17/2017.
 */

public class SignUpRequest implements BaseRequest {
    @SerializedName("username")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String fullName;
    @SerializedName("mobile_phone")
    private String phone;
    @SerializedName("id_card")
    private String cmt;
    @SerializedName("address")
    private String address;
    @Override
    public String getUrl() {
        Uri.Builder builder = Uri.parse(Config.API_URL).buildUpon();
        builder.appendPath(ApiConfig.API);
        builder.appendPath(ApiConfig.API_USER);
        return builder.toString();
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder()
                .add("Content-Type", "application/json");
        return builder.build();
    }

    @Override
    public String getMethod() {
        return ApiConfig.METHOD_POST;
    }

    @Override
    public RequestBody getBody() {
        RequestBody body = RequestBody.create(JSON, toJson());
        return body;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
