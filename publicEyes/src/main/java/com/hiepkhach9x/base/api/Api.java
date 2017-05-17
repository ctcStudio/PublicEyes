package com.hiepkhach9x.base.api;

/**
 * Created by hungh on 5/17/2017.
 */

public interface Api {
    void startRequest(int requestId, BaseRequest request, ResponseListener responseListener);
    void restartRequest(int requestId, BaseRequest request, ResponseListener responseListener);
}
