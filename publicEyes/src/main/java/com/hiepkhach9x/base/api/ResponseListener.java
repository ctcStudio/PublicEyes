package com.hiepkhach9x.base.api;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HungHN on 7/18/16.
 */
public interface ResponseListener {

    BaseResponse parse(int requestId, Call call, Response response) throws Exception;

    void onResponse(int requestId, BaseResponse response);

    void onError(int requestId, Exception e);
}
