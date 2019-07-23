package com.project.sampleunittest.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.project.sampleunittest.main.MovieAdapter.MovieViewHolder;
import com.project.sampleunittest.databinding.ItemMovieSearchBinding;
import com.project.sampleunittest.data.MovieResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private LayoutInflater layoutInflater;

    protected Context context;

    protected List<MovieResponse> mResponseList = new ArrayList<>();

    private ShowMovieDetailListener listener;

    public MovieAdapter(final Context context, List<MovieResponse> responseList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        mResponseList = responseList;
    }

    public void setListener(final ShowMovieDetailListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        ItemMovieSearchBinding item = ItemMovieSearchBinding.inflate(layoutInflater, viewGroup, false);
        return new MovieViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, final int i) {
        MovieResponse movie = mResponseList.get(i);
        movieViewHolder.binding.setMovie(movie);
        movieViewHolder.binding.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return mResponseList == null ? 0 : mResponseList.size();
    }

    protected static class MovieViewHolder extends RecyclerView.ViewHolder {

        public ItemMovieSearchBinding binding;

        public MovieViewHolder(ItemMovieSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
    public interface ShowMovieDetailListener {

        void showDetail(MovieResponse movie);
    }
}
