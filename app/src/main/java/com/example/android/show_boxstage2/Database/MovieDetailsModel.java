package com.example.android.show_boxstage2.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.android.show_boxstage2.Database.TypeConverters.CastConverter;
import com.example.android.show_boxstage2.Database.TypeConverters.GenreConverter;
import com.example.android.show_boxstage2.Database.TypeConverters.ReviewConverter;
import com.example.android.show_boxstage2.Database.TypeConverters.VideoConverter;
import com.example.android.show_boxstage2.Models.Cast;
import com.example.android.show_boxstage2.Models.Genre_POJO;
import com.example.android.show_boxstage2.Models.Reviews_POJO;
import com.example.android.show_boxstage2.Models.Videos_POJO;

import java.util.List;

@Entity(tableName = "movieDetails")
public class MovieDetailsModel {


    @PrimaryKey
    @NonNull
    private String movieId;
    private String title;
    private String posterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;
    private String backdrop_path;
    private String status;
    private String runtime;
    private String tagline;

    //Genre
    @ColumnInfo(name = "genrelist")
    @TypeConverters(GenreConverter.class)
    private List<Genre_POJO> genres;

    //video
    @ColumnInfo(name = "trailerlist")
    @TypeConverters(VideoConverter.class)
    private List<Videos_POJO> videos;
    //cast
    @ColumnInfo(name = "casts")
    @TypeConverters(CastConverter.class)
    private  List<Cast> cast;

    //Review
    @ColumnInfo(name = "reviews")
    @TypeConverters(ReviewConverter.class)
    private  List<Reviews_POJO> reviews;


    public MovieDetailsModel(String movieId, String title, String posterPath, String overview, String voteAverage, String releaseDate, String backdrop_path, String status, String runtime, String tagline, List<Genre_POJO> genres, List<Videos_POJO> videos, List<Cast> cast, List<Reviews_POJO> reviews) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.backdrop_path = backdrop_path;
        this.status = status;
        this.runtime = runtime;
        this.tagline = tagline;
        this.genres = genres;
        this.videos = videos;
        this.cast = cast;
        this.reviews = reviews;
    }


    public String getMovieId() {
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

    public String getStatus() {
        return status;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public List<Genre_POJO> getGenres() {
        return genres;
    }

    public List<Videos_POJO> getVideos() {
        return videos;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Reviews_POJO> getReviews() {
        return reviews;
    }





}
