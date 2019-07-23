package com.project.sampleunittest.data;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    public static final int STATUS_CODE_SUCCESS = 0;
    @SerializedName("status_code")
    private int status_code;

    @SerializedName("status_message")
    private String status_message;

    public int getStatus_code() {
        if(status_code != STATUS_CODE_SUCCESS){
            return status_code;
        }
        return STATUS_CODE_SUCCESS;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(final String status_message) {
        this.status_message = status_message;
    }
}
