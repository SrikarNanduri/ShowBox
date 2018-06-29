package com.example.android.show_boxstage2.Database.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "movieDetails")
public class MovieDetailsModel {

    @PrimaryKey
    private String movieId;
    private String title;
    private String posterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;
    private String backdrop_path;
    private String budget;
    private String status;
    private String runtime;
    private String revenue;
    private String tagline;

    //Genre
    private List<GenreModel> genres;

    //video
    private String key; // video key


    //cast
    private String profilePath;
    private String name;
    private String character;

    //Review
    private String author;
    private String content;
    
    

    public MovieDetailsModel(String movieId, String title, String posterPath, String overview, String voteAverage, String releaseDate, String backdrop_path, String budget, String status, String runtime, String revenue, String tagline, List<GenreModel> genres, String key, String profilePath, String name, String character, String author, String content) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.status = status;
        this.runtime = runtime;
        this.revenue = revenue;
        this.tagline = tagline;
        this.genres = genres;
        this.key = key;
        this.profilePath = profilePath;
        this.name = name;
        this.character = character;
        this.author = author;
        this.content = content;
    }

    
    public String getId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getBudget() {
        return budget;
    }

    public String getStatus() {
        return status;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getTagline() {
        return tagline;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public String getKey() {
        return key;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }



}
