package com.project.sampleunittest;

import static android.support.v4.util.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.content.Context;
import com.project.sampleunittest.network.MovieDBClient;
import com.project.sampleunittest.network.MovieDBService;
import com.project.sampleunittest.data.MovieRepository;
import io.reactivex.annotations.NonNull;

/**
 * This is useful for testing, since it allows us to use
 *  a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class InjectRepository {
    @SuppressLint("RestrictedApi")
    public static MovieRepository provideRepository(@NonNull Context context) {
        checkNotNull(context);
//        ToDoDatabase database = ToDoDatabase.getInstance(context);
        MovieDBService movieDBService = MovieDBClient.getInstance();
        return MovieRepository.getInstance(movieDBService);
    }
}
