package com.btxdev.tmdb.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/movie/popular?")
    Call<Movies> getMoviesByPopularity(@Query("language") String language, @Query("page") String page);

    @GET("/3/search/movie?")
    Call<Movies> searchMovies(@Query("query") String query, @Query("page") String page);

    @GET("3/genre/movie/list")
    Call<Genres> getMovieGenreList();


}
