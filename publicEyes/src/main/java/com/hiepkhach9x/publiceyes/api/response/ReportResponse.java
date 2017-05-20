package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.Complaint;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by HungHN on 5/18/17.
 */

public class ReportResponse extends BaseResponse {
    @SerializedName("Data")
    private ArrayList<Complaint> complaints;

    public ReportResponse(String response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        ReportResponse response = gson.fromJson(data,ReportResponse.class);
        code = response.getCode();
        complaints = response.getComplaints();
    }

    public ArrayList<Complaint> getComplaints() {
        return complaints;
    }

    @Override
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }
}
