package com.btxdev.tmdb.interactor;

import com.btxdev.tmdb.App;
import com.btxdev.tmdb.R;
import com.btxdev.tmdb.api.ApiClient;
import com.btxdev.tmdb.api.ApiInterface;
import com.btxdev.tmdb.api.Movies;
import com.btxdev.tmdb.presenter.MoviesPresenter;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesInteractorImpl implements MoviesInteractor{
    private MoviesPresenter moviesPresenter;
    private ApiInterface apiInterface;

    public MoviesInteractorImpl(MoviesPresenter moviesPresenter){
        this.moviesPresenter = moviesPresenter;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getMovies(int page) {
        String pageString = Integer.toString(page);
        Call<Movies> call = apiInterface.getMoviesByPopularity("en-US",pageString);
        moviesPresenter.showLoading();
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NotNull Call<Movies> call, @NotNull Response<Movies> response) {
                if(response.body()==null){
                    moviesPresenter.showError(new Exception(App.getInstance().getString(R.string.empty_response_received)));
                }else{
                    if(response.body().results==null||response.body().results.size()==0){
                        moviesPresenter.showMessage(App.getInstance().getString(R.string.no_more_results));
                    }else{
                        moviesPresenter.showMovies(response.body().results);
                    }
                }
                moviesPresenter.showLoadingFinish();

            }

            @Override
            public void onFailure(@NotNull Call<Movies> call, @NotNull Throwable t) {
                moviesPresenter.showError(new Exception(App.getInstance().getString(R.string.cannot_connect_to_the_server)));
                moviesPresenter.showLoadingFinish();
            }
        });
    }

    @Override
    public void searchMovies(String query, int page) {
        String pageString = Integer.toString(page);
        Call<Movies> call = apiInterface.searchMovies(query, pageString);
        moviesPresenter.showLoading();
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NotNull Call<Movies> call, @NotNull Response<Movies> response) {
                if(response.body()==null){
                    moviesPresenter.showError(new Exception(App.getInstance().getString(R.string.empty_response_received)));
                }else{
                    if(response.body().results==null||response.body().results.size()==0){
                        moviesPresenter.showMessage(App.getInstance().getString(R.string.no_more_results));
                    }else{
                        moviesPresenter.showSearchResults(response.body().results);
                    }
                }
                moviesPresenter.showLoadingFinish();

            }

            @Override
            public void onFailure(@NotNull Call<Movies> call, @NotNull Throwable t) {
                moviesPresenter.showError(new Exception(App.getInstance().getString(R.string.cannot_connect_to_the_server)));
                moviesPresenter.showLoadingFinish();
            }
        });
    }
}
