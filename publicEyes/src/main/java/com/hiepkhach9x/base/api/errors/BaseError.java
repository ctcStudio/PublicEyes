package com.hiepkhach9x.base.api.errors;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HungHN on 7/18/16.
 */
public abstract class BaseError extends Exception {
    protected Call call;
    protected Response response;

    public BaseError(Call call, Response response) {
        this.call = call;
        this.response = response;
    }

    public Call getCall() {
        return call;
    }

    public Response getResponse() {
        return response;
    }
}
