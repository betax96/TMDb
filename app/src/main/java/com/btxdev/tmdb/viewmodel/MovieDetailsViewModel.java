package com.btxdev.tmdb.viewmodel;

import androidx.lifecycle.ViewModel;

import com.btxdev.tmdb.api.Movie;

public class MovieDetailsViewModel extends ViewModel {
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
