package com.btxdev.tmdb.interactor;

public interface MoviesInteractor {
    void requestMovies(int page);
    void searchMovies(String query, int page);
}
