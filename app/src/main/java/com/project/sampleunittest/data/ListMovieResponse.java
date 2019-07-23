package com.project.sampleunittest.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListMovieResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieResponse> mListMovie;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public List<MovieResponse> getListMovie() {
        return mListMovie;
    }

    public void setListMovie(final List<MovieResponse> listMovie) {
        mListMovie = listMovie;
    }
}
