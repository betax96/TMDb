package com.btxdev.tmdb.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Genres {
    @SerializedName("genres")
    public List<Genre> genres;

    public Genre findGenre(int id){
        for(Genre genre: genres){
            if(genre.id == id){
                return genre;
            }
        }
        return null;
    }

    public List<Genre> findGenres(List<Integer> genreIds){
        List<Genre> foundGenres = new ArrayList<>();
        for(Genre genre: genres){
            if(genreIds.contains(genre.id)){
                foundGenres.add(genre);
            }
        }
        return foundGenres;
    }
}
