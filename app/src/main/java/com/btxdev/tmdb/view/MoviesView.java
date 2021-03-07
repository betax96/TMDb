package com.btxdev.tmdb.view;

import com.btxdev.tmdb.api.Movie;

import java.util.List;

public interface MoviesView {
    void showMovies(List<Movie> movies);
    void showError(Exception e);
    void showMessage(String message);
    void showLoading();
    void showLoadingFinish();
    void showSearchResults(List<Movie> movies);
}
