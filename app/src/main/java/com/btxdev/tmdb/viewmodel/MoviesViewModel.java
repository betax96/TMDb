package com.btxdev.tmdb.viewmodel;

import androidx.lifecycle.ViewModel;

import com.btxdev.tmdb.api.Movie;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private List<Movie> moviesList;
    private List<Movie> moviesTemp;
    private String currentSearch;
    private int currentPage;
    private int nextPage;
    private int currentPageSearch;
    private int nextPageSearch;

    public MoviesViewModel(){
        currentPage = 1;
        nextPage = 2;
        currentPageSearch = 1;
        nextPageSearch = 2;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    public List<Movie> getMoviesTemp() {
        return moviesTemp;
    }

    public void setMoviesTemp(List<Movie> moviesTemp) {
        this.moviesTemp = moviesTemp;
    }

    public String getCurrentSearch() {
        return currentSearch;
    }

    public void setCurrentSearch(String currentSearch) {
        this.currentSearch = currentSearch;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getCurrentPageSearch() {
        return currentPageSearch;
    }

    public void setCurrentPageSearch(int currentPageSearch) {
        this.currentPageSearch = currentPageSearch;
    }

    public int getNextPageSearch() {
        return nextPageSearch;
    }

    public void setNextPageSearch(int nextPageSearch) {
        this.nextPageSearch = nextPageSearch;
    }
}
