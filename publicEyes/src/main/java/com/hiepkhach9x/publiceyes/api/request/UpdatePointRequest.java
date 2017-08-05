package com.hiepkhach9x.publiceyes.api.request;

import android.net.Uri;

import com.google.gson.Gson;
import com.hiepkhach9x.base.api.BaseRequest;
import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.api.ApiConfig;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.entities.TransactionPoint;
import com.hiepkhach9x.publiceyes.store.UserPref;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by HungHN on 5/19/17.
 */

public class UpdatePointRequest implements BaseRequest {
    private TransactionPoint transactionPoint;
    @Override
    public String getUrl() {
        Uri.Builder builder = Uri.parse(Config.API_URL).buildUpon();
        builder.appendPath(ApiConfig.API);
        builder.appendPath(ApiConfig.API_UPDATE_POINT);
        return builder.toString();
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(transactionPoint);
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
        RequestBody body = RequestBody.create(JSON, toJson());
        return body;
    }

    public void setTransactionPoint(TransactionPoint transactionPoint) {
        this.transactionPoint = transactionPoint;
    }
}