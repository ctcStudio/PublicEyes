package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.Category;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HungHN on 5/19/17.
 */

public class GetListCategoryResponse extends BaseResponse {
    @SerializedName("Data")
    private ArrayList<Category> categories;

    public GetListCategoryResponse(String response) throws IOException, JSONException, JsonSyntaxException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        GetListCategoryResponse response = gson.fromJson(data, GetListCategoryResponse.class);
        code = response.getCode();
        categories = response.getCategories();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }
}
