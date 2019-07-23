package com.project.sampleunittest.detail;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.project.sampleunittest.R;
import com.project.sampleunittest.ViewModelFactory;
import com.project.sampleunittest.databinding.ActivityMovieDetailBinding;

public class MovieDetail extends AppCompatActivity {

    private static final String EXTRA_ID_MOVIE = "EXTRA_ID_MOVIE";

    private DetailViewModel mDetailViewModel;

    private ActivityMovieDetailBinding mDetailBinding;

    public static void start(Context context, int id){
        Intent intent = new Intent(context, MovieDetail.class);
        intent.putExtra(EXTRA_ID_MOVIE, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        mDetailBinding.setLifecycleOwner(this);

        initViewModel();

        initView();
    }

    private void initView() {
        mDetailViewModel.getMovie().observe(this, movieResponse -> {
            mDetailBinding.setMovie(movieResponse);
        });
    }

    private void initViewModel() {
        int id = getIntent().getIntExtra(EXTRA_ID_MOVIE, -1);
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        mDetailViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        mDetailViewModel.getDetailMovie(id);
    }
}
