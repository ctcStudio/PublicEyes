package com.hiepkhach9x.base.api.errors;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HungHN on 7/18/16.
 */
public class AuthFailureError extends BaseError {

    public AuthFailureError(Call call, Response response) {
        super(call, response);
    }
}
