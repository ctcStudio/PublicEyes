package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.entities.News;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HungHN on 5/18/17.
 */

public class GetCompaignsResponse extends BaseResponse {
    @SerializedName("Code")
    private int code;
    @SerializedName("Data")
    private ArrayList<News> newses;

    public GetCompaignsResponse(String response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        GetCompaignsResponse response = gson.fromJson(data,GetCompaignsResponse.class);
        code = response.getCode();
        newses = response.getNewses();
    }

    public int getCode() {
        return code;
    }

    public ArrayList<News> getNewses() {
        return newses;
    }
}
