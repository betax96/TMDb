package com.btxdev.tmdb.viewmodel;

import androidx.lifecycle.ViewModel;

import com.btxdev.tmdb.api.Genre;
import com.btxdev.tmdb.api.Movie;

import java.util.List;

public class MovieDetailsViewModel extends ViewModel {
    private Movie movie;
    private List<Genre> genres;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
