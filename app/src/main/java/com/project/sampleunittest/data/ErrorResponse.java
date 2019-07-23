package com.project.sampleunittest.data;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class ErrorResponse {
    @SerializedName("status_code")
    private String status_code;

    @SerializedName("status_message")
    private String status_message;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(final String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(final String status_message) {
        this.status_message = status_message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorResponse)) {
            return false;
        }
        final ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(status_code, that.status_code) &&
                Objects.equals(status_message, that.status_message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, status_message);
    }
}
