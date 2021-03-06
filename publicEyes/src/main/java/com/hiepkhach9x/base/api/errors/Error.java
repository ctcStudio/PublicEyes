package com.hiepkhach9x.base.api.errors;

import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.publiceyes.entities.Msg;

/**
 * Created by hungh on 5/1/2017.
 */

public class Error {
    @SerializedName("error")
    private int code;
    @SerializedName("Data")
    private Msg message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Msg getMessage() {
        return message;
    }

    public void setMessage(Msg message) {
        this.message = message;
    }
}
