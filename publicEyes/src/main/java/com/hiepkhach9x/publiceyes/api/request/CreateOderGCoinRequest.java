package com.hiepkhach9x.publiceyes.api.request;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hiepkhach9x.base.api.BaseRequest;
import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.api.ApiConfig;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by HungHN on 5/18/17.
 */

public class CreateOderGCoinRequest implements BaseRequest {
    private String transRef;
    private int amount;
    private String userNophone; // Định danh ví của User, dưới dạng số điện thoại. Chú ý là phải bắt đầu bằng '+84'
                                // nhưng do query trên url nên kí tự '+' được thể hiện là '%2B'
    private String callbackData;
    private String userId;
    private String serviceId;

    @Override
    public String getUrl() {
        Uri.Builder builder = Uri.parse(Config.API_COIN_URL).buildUpon();
        builder.appendPath(ApiConfig.API_COIN_SEND_ORDER);
        builder.appendQueryParameter("transRef",transRef);
        builder.appendQueryParameter("userNophone", userNophone);
        builder.appendQueryParameter("amount",String.valueOf(amount));
        builder.appendQueryParameter("callback_data",callbackData);
        if (!TextUtils.isEmpty(userId)) {
            builder.appendQueryParameter("userId", userId);
        }
        if (!TextUtils.isEmpty(serviceId)) {
            builder.appendQueryParameter("serviceId", serviceId);
        }

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
        builder.add("Authorization", ApiConfig.getCoinAuth());
        String nonce = ApiConfig.getUnixTime();
        builder.add("X-Nonce", nonce);
        try {
            String hashNonce = ApiConfig.hashSha256(nonce);
            String urlHash = getUrl().replace(Config.COIN_URL,"");
            byte[] hashData = ApiConfig.hashHmacSHA512(urlHash + hashNonce, ApiConfig.SECRET);
            String signature = ApiConfig.bytesToHex(hashData);
            builder.add("X-Signature", signature);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @Override
    public String getMethod() {
        return ApiConfig.METHOD_GET;
    }

    @Override
    public RequestBody getBody() {
        return null;
    }

    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUserNophone() {
        return userNophone;
    }

    public void setUserNophone(String userNophone) {
        this.userNophone = userNophone;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
