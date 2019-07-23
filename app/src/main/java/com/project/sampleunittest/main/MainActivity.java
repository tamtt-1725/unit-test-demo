package com.project.sampleunittest.main;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.project.sampleunittest.data.MovieResponse;
import com.project.sampleunittest.detail.MovieDetail;
import com.project.sampleunittest.main.MovieAdapter.ShowMovieDetailListener;
import com.project.sampleunittest.R;
import com.project.sampleunittest.ViewModelFactory;
import com.project.sampleunittest.databinding.ActivityMainBinding;
import com.project.sampleunittest.data.FetchDataStatus;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements ShowMovieDetailListener {

    private MainViewModel mMainViewModel;

    private ProgressDialog dialog;

    private MovieAdapter mMovieAdapter;

    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setLifecycleOwner(this);

        initViewModel();

        initView();

        mMainViewModel.getListPopular();

        mMainViewModel.getLoadDataStatus().observe(this, this::showLoading);
    }

    @Override
    public void showDetail(final MovieResponse pos) {
        MovieDetail.start(this, pos.getId());
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMainBinding.rcvListMovie.setLayoutManager(layoutManager);
        mMovieAdapter = new MovieAdapter(this, mMainViewModel.getList());
        mMainBinding.rcvListMovie.setAdapter(mMovieAdapter);
        mMovieAdapter.setListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FetchDataStatus event) {
        switch (event.getStatus()) {
            case MainViewModel.GET_LIST_POPULAR_DONE:
                mMovieAdapter.notifyDataSetChanged();
                break;
            case MainViewModel.GET_LIST_POPULAR_FAILURE:

                break;
        }
    }

    private void initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    public void showLoading(final boolean isShowing) {
        runOnUiThread(() -> {
            if (isFinishing()) {
                return;
            }
            try {
                if (isShowing) {
                    load();
                } else {
                    unload();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void unload() {
        if (dialog == null) {
            return;
        }
        dialog.dismiss();
    }

    private void load() {
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
    }
}
