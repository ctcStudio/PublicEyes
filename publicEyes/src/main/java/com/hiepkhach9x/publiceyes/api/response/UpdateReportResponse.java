package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.Msg;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by HungHN on 5/19/17.
 */

public class UpdateReportResponse extends BaseResponse {
    @SerializedName("Data")
    private Msg msg;

    public UpdateReportResponse(String response) throws IOException, JSONException, JsonSyntaxException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        UpdateReportResponse response = gson.fromJson(data, UpdateReportResponse.class);
        code = response.getCode();
        msg = response.getMsg();
    }

    public Msg getMsg() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }
}
