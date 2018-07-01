package com.example.android.show_boxstage2.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
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
public class MovieDetailsModel implements Parcelable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    private String movieId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "posterPath")
    private String posterPath;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "voteAverage")
    private String voteAverage;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "runtime")
    private String runtime;
    @ColumnInfo(name = "tagline")
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


    protected MovieDetailsModel(Parcel in) {
        movieId = in.readString();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        backdrop_path = in.readString();
        status = in.readString();
        runtime = in.readString();
        tagline = in.readString();
        genres = in.createTypedArrayList(Genre_POJO.CREATOR);
        videos = in.createTypedArrayList(Videos_POJO.CREATOR);
        cast = in.createTypedArrayList(Cast.CREATOR);
        reviews = in.createTypedArrayList(Reviews_POJO.CREATOR);
    }

    public static final Creator<MovieDetailsModel> CREATOR = new Creator<MovieDetailsModel>() {
        @Override
        public MovieDetailsModel createFromParcel(Parcel in) {
            return new MovieDetailsModel(in);
        }

        @Override
        public MovieDetailsModel[] newArray(int size) {
            return new MovieDetailsModel[size];
        }
    };

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



    @Override
    public String toString() {
        return "MovieDetailsModel{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", status='" + status + '\'' +
                ", runtime='" + runtime + '\'' +
                ", tagline='" + tagline + '\'' +
                ", genres=" + genres +
                ", videos=" + videos +
                ", cast=" + cast +
                ", reviews=" + reviews +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(backdrop_path);
        dest.writeString(status);
        dest.writeString(runtime);
        dest.writeString(tagline);
        dest.writeTypedList(genres);
        dest.writeTypedList(videos);
        dest.writeTypedList(cast);
        dest.writeTypedList(reviews);
    }
}
