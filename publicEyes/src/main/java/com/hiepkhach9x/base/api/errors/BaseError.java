package com.hiepkhach9x.base.api.errors;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HungHN on 7/18/16.
 */
public abstract class BaseError extends Exception {
    protected Call call;
    protected String response;

    public BaseError(Call call, String response) {
        this.call = call;
        this.response = response;
    }

    public Call getCall() {
        return call;
    }

    public String getResponse() {
        return response;
    }
}
