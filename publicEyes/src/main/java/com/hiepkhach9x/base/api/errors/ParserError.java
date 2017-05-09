package com.hiepkhach9x.base.api.errors;

import com.hiepkhach9x.base.api.BaseResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hungh on 4/22/2017.
 */

public class ParserError extends BaseError {
    public ParserError(Call call, Response response) {
        super(call, response);
    }
}
