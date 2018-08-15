package com.xsy.android.videoplayer.model;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/29.
 */

public class Movies {
    private String title;
    ArrayList<Movie> movies;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
