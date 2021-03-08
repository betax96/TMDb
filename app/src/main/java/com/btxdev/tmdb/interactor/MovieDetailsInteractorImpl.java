package com.btxdev.tmdb.interactor;

import com.btxdev.tmdb.api.ApiClient;
import com.btxdev.tmdb.api.ApiInterface;
import com.btxdev.tmdb.api.Genre;
import com.btxdev.tmdb.api.Genres;
import com.btxdev.tmdb.api.Movie;
import com.btxdev.tmdb.presenter.MovieDetailsPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsInteractorImpl implements MovieDetailsInteractor {
    private MovieDetailsPresenter movieDetailsPresenter;
    private ApiInterface apiInterface;


    public MovieDetailsInteractorImpl(MovieDetailsPresenter movieDetailsPresenter) {
        this.movieDetailsPresenter = movieDetailsPresenter;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getMovieDetails(Movie movie) {
        movieDetailsPresenter.showMovieDetails(movie.title , movie.releaseDate, String.valueOf(((int) movie.popularity)), movie.overview,
                "https://image.tmdb.org/t/p/w500"+movie.posterPath);
    }

    @Override
    public void getGenreDetails(Movie movie) {
        Call<Genres> call = apiInterface.getMovieGenreList();
        call.enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                if(response.body()==null){
                    movieDetailsPresenter.showMovieGenreDetailsNotAvailable();
                }else{
                    if(response.body().genres==null||response.body().genres.size()==0){
                        movieDetailsPresenter.showMovieGenreDetailsNotAvailable();
                    }else{
                        List<Genre> movieGenres = response.body().findGenres(movie.genreIds);
                        if(movieGenres.size()>0){
                            movieDetailsPresenter.showMovieGenreDetails(movieGenres);
                        }else{
                            movieDetailsPresenter.showMovieGenreDetailsNotAvailable();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Genres> call, Throwable t) {
                movieDetailsPresenter.showMovieGenreDetailsNotAvailable();
            }
        });
    }
}
