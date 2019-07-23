package com.project.sampleunittest.network;

import com.project.sampleunittest.data.ListMovieResponse;
import com.project.sampleunittest.data.MovieRepository;
import com.project.sampleunittest.data.MovieResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/popular")
    Single<ListMovieResponse> getListPopular(@Query("api_key") String key);

    @GET("movie/{movie_id}")
    Single<MovieResponse> getDetail(@Path("movie_id") int id, @Query("api_key") String key);

}
