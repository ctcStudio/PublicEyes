package com.hiepkhach9x.publiceyes.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hunghoang on 8/5/17.
 */

public class TransactionPoint {
    @SerializedName("transcation_id")
    private String transactionId;
    @SerializedName("point")
    private int point;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
