package com.btxdev.tmdb.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesByPopularity {
    @SerializedName("page")
    public int page;
    @SerializedName("results")
    public List<Movie> results;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("total_results")
    public int totalResults;
}
