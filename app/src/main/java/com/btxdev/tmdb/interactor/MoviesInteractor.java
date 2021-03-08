package com.btxdev.tmdb.interactor;

public interface MoviesInteractor {
    void getMovies(int page);
    void searchMovies(String query, int page);
}
