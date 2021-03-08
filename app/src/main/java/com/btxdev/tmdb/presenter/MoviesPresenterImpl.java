package com.btxdev.tmdb.presenter;

import com.btxdev.tmdb.api.Movie;
import com.btxdev.tmdb.interactor.MoviesInteractor;
import com.btxdev.tmdb.interactor.MoviesInteractorImpl;
import com.btxdev.tmdb.view.MoviesView;

import java.util.List;

public class MoviesPresenterImpl implements MoviesPresenter{

    private MoviesView moviesView;
    private MoviesInteractor moviesInteractor;

    public MoviesPresenterImpl(MoviesView moviesView){
        this.moviesView = moviesView;
        moviesInteractor = new MoviesInteractorImpl(this);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        moviesView.showMovies(movies);
    }

    @Override
    public void showError(Exception e) {
        moviesView.showError(e);
    }

    @Override
    public void showLoading() {
        moviesView.showLoading();
    }

    @Override
    public void showLoadingFinish() {
        moviesView.showLoadingFinish();
    }

    @Override
    public void showMessage(String message) {
        moviesView.showMessage(message);
    }

    @Override
    public void showSearchResults(List<Movie> movies) {
        moviesView.showSearchResults(movies);
    }

    @Override
    public void getMovies(int page) {
        moviesInteractor.getMovies(page);
    }

    @Override
    public void searchMovies(String query, int page) {
        moviesInteractor.searchMovies(query, page);
    }
}
