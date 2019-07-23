package com.project.sampleunittest.detail;

import android.arch.lifecycle.MutableLiveData;
import com.project.sampleunittest.data.BaseViewModel;
import com.project.sampleunittest.data.FetchDataStatus;
import com.project.sampleunittest.data.MovieRepository;
import com.project.sampleunittest.data.MovieResponse;
import io.reactivex.schedulers.Schedulers;
import org.greenrobot.eventbus.EventBus;

public class DetailViewModel extends BaseViewModel {

    public static final String GET_DETAIL_MOVIE_FAILURE = "GET_DETAIL_MOVIE_FAILURE";

    private MovieRepository mMovieRepository;

    private MutableLiveData<MovieResponse>  movie = new MutableLiveData<>();

    private MutableLiveData<Boolean> loadDataStatus = new MutableLiveData<>();

    public DetailViewModel(final MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    public void setMovieRepository(final MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    public MutableLiveData<MovieResponse> getMovie() {
        return movie;
    }

    public void setMovie(final MovieResponse movie) {
        this.movie.postValue(movie);
    }

    public MutableLiveData<Boolean> getLoadDataStatus() {
        return loadDataStatus;
    }
    public void getDetailMovie(int id){
        subscribe(mMovieRepository.getDetail(id)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposable -> getLoadDataStatus().postValue(true))
                .doAfterTerminate(() -> getLoadDataStatus().postValue(false))
                .subscribe(response -> {
                    if (response.getStatus_code() == MovieResponse.STATUS_CODE_SUCCESS) {
                        setMovie(response);
                    } else {
                        EventBus.getDefault().post(new FetchDataStatus(GET_DETAIL_MOVIE_FAILURE));
                    }
                }, throwable -> handleError(throwable)));
    }
}
