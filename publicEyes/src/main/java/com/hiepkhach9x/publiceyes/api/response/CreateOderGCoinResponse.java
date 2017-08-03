package com.hiepkhach9x.publiceyes.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.entities.News;
import com.hiepkhach9x.publiceyes.entities.OrderGCoin;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HungHN on 5/18/17.
 */

public class CreateOderGCoinResponse extends BaseResponse {
    private OrderGCoin orderGCoin;

    public CreateOderGCoinResponse(String response) throws IOException, JSONException {
        super(response);
    }

    @Override
    protected void parseData(String data) throws IOException, JSONException {
        Gson gson = new Gson();
        orderGCoin = gson.fromJson(data,OrderGCoin.class);
    }

    public OrderGCoin getOrderGCoins() {
        return orderGCoin;
    }

    @Override
    public boolean isSuccess() {
        return orderGCoin != null;
    }
}
