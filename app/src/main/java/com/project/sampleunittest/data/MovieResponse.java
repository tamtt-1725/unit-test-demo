package com.project.sampleunittest.data;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class MovieResponse extends BaseResponse{

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("overview")
    private String overview;

    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("vote_average")
    private float vote_average;

    @SerializedName("release_date")
    private String release_date;


    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(final String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(final String original_title) {
        this.original_title = original_title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(final float vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(final String release_date) {
        this.release_date = release_date;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieResponse)) {
            return false;
        }
        final MovieResponse that = (MovieResponse) o;
        if (getStatus_code() == STATUS_CODE_SUCCESS) {
            return id == that.id;
        } else {
            return getStatus_code() == that.getStatus_code();
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
