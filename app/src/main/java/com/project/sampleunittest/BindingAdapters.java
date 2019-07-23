package com.project.sampleunittest;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.project.sampleunittest.data.MovieResponse;

public class BindingAdapters {

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    @BindingAdapter("loadImageUser")
    public static void loadImage(ImageView imageView, MovieResponse movie) {
        String urlImg;
        if (movie == null || movie.getPoster_path() == null) {
            urlImg = "";
        } else {
            urlImg = BASE_IMAGE_URL.concat(movie.getPoster_path());
        }
        Glide.with(imageView.getContext())
                .load(urlImg)
                .into(imageView);
    }

}
