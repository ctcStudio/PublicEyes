package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.News;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HungHN on 5/18/17.
 */

public class GetCampaignsResponse extends BaseResponse {
    @SerializedName("Data")
    private ArrayList<News> newses;

    public GetCampaignsResponse(String response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        GetCampaignsResponse response = gson.fromJson(data,GetCampaignsResponse.class);
        code = response.getCode();
        newses = response.getNewses();
    }

    public ArrayList<News> getNewses() {
        return newses;
    }

    @Override
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }
}
