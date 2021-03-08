package com.btxdev.tmdb.view;

import com.btxdev.tmdb.api.Genre;

import java.util.List;

public interface MovieDetailsView {
    void showMovieDetails(String title, String releaseDate, String popularityScore, String summary, String pictureUrl);
    void showMovieGenreDetails(List<Genre> genres);
    void showMovieGenreDetailsNotAvailable();

}
