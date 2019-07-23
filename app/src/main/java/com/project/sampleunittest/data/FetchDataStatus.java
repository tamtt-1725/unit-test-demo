package com.project.sampleunittest.data;

import java.util.Objects;

public class FetchDataStatus {
    private String status;

    public FetchDataStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FetchDataStatus)) {
            return false;
        }
        final FetchDataStatus that = (FetchDataStatus) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
