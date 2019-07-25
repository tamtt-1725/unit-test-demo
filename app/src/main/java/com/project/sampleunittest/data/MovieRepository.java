package com.project.sampleunittest.data;

import com.project.sampleunittest.network.MovieDBService;
import io.reactivex.Single;

public class MovieRepository extends BaseRepository {

    private String API_KEY = "3956f50a726a2f785334c24759b97dc6";

    private static MovieRepository instance;

    public static synchronized MovieRepository getInstance(MovieDBService service) {
        if (instance == null) {
            instance = new MovieRepository(service);
        }
        return instance;
    }

    private MovieRepository(MovieDBService service) {
        super(service);
    }

    public Single<ListMovieResponse> getListPopular() {
        return service.getListPopular(API_KEY);
    }

    public Single<MovieResponse> getDetail(int id) {
        return service.getDetail(id, API_KEY);
    }

    public int cong(int a, int b) {
        return a + b;
    }
}
