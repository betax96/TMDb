package com.btxdev.tmdb.presenter;

import com.btxdev.tmdb.api.Genre;
import com.btxdev.tmdb.api.Movie;
import com.btxdev.tmdb.interactor.MovieDetailsInteractor;
import com.btxdev.tmdb.interactor.MovieDetailsInteractorImpl;
import com.btxdev.tmdb.view.MovieDetailsView;

import java.util.List;

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter {

    private MovieDetailsView movieDetailsView;
    private MovieDetailsInteractor movieDetailsInteractor;

    public MovieDetailsPresenterImpl(MovieDetailsView movieDetailsView) {
        this.movieDetailsView = movieDetailsView;
        movieDetailsInteractor = new MovieDetailsInteractorImpl(this);
    }

    @Override
    public void showMovieDetails(String title, String releaseDate, String popularityScore, String summary, String pictureUrl) {
        movieDetailsView.showMovieDetails(title, releaseDate,popularityScore,summary, pictureUrl);
    }

    @Override
    public void showMovieGenreDetails(List<Genre> genres) {
        movieDetailsView.showMovieGenreDetails(genres);
    }

    @Override
    public void showMovieGenreDetailsNotAvailable() {
        movieDetailsView.showMovieGenreDetailsNotAvailable();
    }

    @Override
    public void getMovieDetails(Movie movie) {
        movieDetailsInteractor.getMovieDetails(movie);
    }

    @Override
    public void getGenreDetails(Movie movie) {
        movieDetailsInteractor.getGenreDetails(movie);
    }
}
