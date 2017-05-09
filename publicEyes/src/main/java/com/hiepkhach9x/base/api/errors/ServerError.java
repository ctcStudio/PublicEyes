package com.hiepkhach9x.base.api.errors;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HungHN on 7/18/16.
 */
public class ServerError extends BaseError {

    private Error error;

    public ServerError(Call call, Response response) {
        super(call, response);
        parseError();
    }

    private void parseError() {
        try {
            Gson gson = new Gson();
            error = gson.fromJson(response.body().string(), Error.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Error getError() {
        return error;
    }
}
