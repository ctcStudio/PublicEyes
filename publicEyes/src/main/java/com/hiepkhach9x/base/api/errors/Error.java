package com.hiepkhach9x.base.api.errors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hungh on 5/1/2017.
 */

public class Error {
    @SerializedName("error")
    String error;
    @SerializedName("error_description")
    String errorDescription;
    @SerializedName("message")
    String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
