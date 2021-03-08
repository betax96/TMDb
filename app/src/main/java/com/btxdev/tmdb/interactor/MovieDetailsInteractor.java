package com.btxdev.tmdb.interactor;

import com.btxdev.tmdb.api.Movie;

public interface MovieDetailsInteractor {
    void getMovieDetails(Movie movie);
    void getGenreDetails(Movie movie);
}
