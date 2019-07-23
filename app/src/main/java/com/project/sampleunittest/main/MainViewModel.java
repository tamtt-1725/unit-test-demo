package com.project.sampleunittest.main;

import android.arch.lifecycle.MutableLiveData;
import com.project.sampleunittest.data.BaseViewModel;
import com.project.sampleunittest.data.FetchDataStatus;
import com.project.sampleunittest.data.MovieRepository;
import com.project.sampleunittest.data.MovieResponse;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class MainViewModel extends BaseViewModel {

    public static final String GET_LIST_POPULAR_DONE = "GET_LIST_POPULAR_DONE";

    public static final String GET_LIST_POPULAR_FAILURE = "GET_LIST_POPULAR_FAILURE";

    private MutableLiveData<Boolean> loadDataStatus = new MutableLiveData<>();

    private List<MovieResponse> mList = new ArrayList<>();

    private MovieRepository mMovieRepository;

    public MainViewModel(MovieRepository repository) {
        this.mMovieRepository = repository;
    }

    public MutableLiveData<Boolean> getLoadDataStatus() {
        return loadDataStatus;
    }

    public List<MovieResponse> getList() {
        return mList;
    }

    public void getListPopular(){
        subscribe(mMovieRepository.getListPopular()
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposable -> getLoadDataStatus().postValue(true))
                .doAfterTerminate(() -> getLoadDataStatus().postValue(false))
                .subscribe(response -> {
                    if (response != null) {
                        EventBus.getDefault().post(new FetchDataStatus(GET_LIST_POPULAR_DONE));
                        mList.addAll(response.getListMovie());
                    } else {
                        EventBus.getDefault().post(new FetchDataStatus(GET_LIST_POPULAR_FAILURE));
                    }
                }, Throwable::printStackTrace));
    }


}
