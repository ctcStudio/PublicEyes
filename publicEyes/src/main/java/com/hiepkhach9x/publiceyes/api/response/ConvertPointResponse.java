package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.Msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by HungHN on 5/19/17.
 */

public class ConvertPointResponse extends BaseResponse {

    private int point;

    public ConvertPointResponse(String response) throws IOException, JSONException, JsonSyntaxException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(data);
        if(jsonObject.has("code")) {
            code = jsonObject.getInt("code");
        }
        if(jsonObject.has("Data")) {
            JSONObject objectData = jsonObject.getJSONObject("Data");
            point = objectData.getInt("point");
        }
    }

    public int getPoint() {
        return point;
    }

    @Override
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }
}
