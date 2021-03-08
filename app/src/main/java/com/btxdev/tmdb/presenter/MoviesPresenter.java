package com.btxdev.tmdb.presenter;

import com.btxdev.tmdb.api.Movie;

import java.util.List;

public interface MoviesPresenter {
    void showMovies(List<Movie> movies);
    void showError(Exception e);
    void showLoading();
    void showLoadingFinish();
    void showMessage(String message);
    void showSearchResults(List<Movie> movies);
    void getMovies(int page);
    void searchMovies(String query, int page);
}
