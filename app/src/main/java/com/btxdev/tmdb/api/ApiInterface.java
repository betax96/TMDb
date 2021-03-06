package com.btxdev.tmdb.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/movie/popular?")
    Call<MoviesByPopularity> getMoviesByPopularity(@Query("language") String language, @Query("page") String page);
}
