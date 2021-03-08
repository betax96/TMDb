package com.btxdev.tmdb.presenter;

import com.btxdev.tmdb.api.Genre;
import com.btxdev.tmdb.api.Movie;

import java.util.List;

public interface MovieDetailsPresenter {
    void showMovieDetails(String title, String releaseDate, String popularityScore, String summary, String pictureUrl);
    void showMovieGenreDetails(List<Genre> genres);
    void showMovieGenreDetailsNotAvailable();
    void getMovieDetails(Movie movie);
    void getGenreDetails(Movie movie);
}
