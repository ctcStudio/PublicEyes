package com.hiepkhach9x.publiceyes.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HungHN on 8/2/17.
 */

public class OrderGCoin {
    @SerializedName("transRef")
    private String transRef;
    @SerializedName("sendOrderId")
    private String sendOrderId;
    @SerializedName("userNophone")
    private String userNoPhone;
    @SerializedName("amount")
    private int amount;
    @SerializedName("address")
    private String address;
    @SerializedName("createOn")
    private String createdOn;
    @SerializedName("time")
    private String time;
    @SerializedName("status")
    private int status;
    @SerializedName("callback_data")
    private String callbackData;
    @SerializedName("error")
    private int error = 200;

    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }

    public String getSendOrderId() {
        return sendOrderId;
    }

    public void setSendOrderId(String sendOrderId) {
        this.sendOrderId = sendOrderId;
    }

    public String getUserNoPhone() {
        return userNoPhone;
    }

    public void setUserNoPhone(String userNoPhone) {
        this.userNoPhone = userNoPhone;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
