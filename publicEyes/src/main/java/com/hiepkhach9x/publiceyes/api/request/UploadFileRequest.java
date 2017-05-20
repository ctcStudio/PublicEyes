package com.hiepkhach9x.publiceyes.api.request;

import android.net.Uri;

import com.google.gson.Gson;
import com.hiepkhach9x.base.api.BaseRequest;
import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.api.ApiConfig;
import com.hiepkhach9x.publiceyes.store.UserPref;

import java.io.File;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by HungHN on 5/18/17.
 */

public class UploadFileRequest implements BaseRequest {
    private File file;
    @Override
    public String getUrl() {
        Uri.Builder builder = Uri.parse(Config.API_URL).buildUpon();
        builder.appendPath(ApiConfig.API);
        builder.appendPath(ApiConfig.API_VIOLATION);
        builder.appendPath(ApiConfig.API_FILE);
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
        builder.add("Authorization", UserPref.get().getAuthorization());
        return builder.build();
    }

    @Override
    public String getMethod() {
        return ApiConfig.METHOD_POST;
    }

    @Override
    public RequestBody getBody() {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file !=null) {
            requestBody.addFormDataPart("file", file.getPath(), RequestBody.create(IMAGE, file));
        }

        return requestBody.build();
    }

    public void setFile(File file) {
        this.file = file;
    }
}
