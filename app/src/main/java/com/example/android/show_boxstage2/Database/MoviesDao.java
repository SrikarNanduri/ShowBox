package com.example.android.show_boxstage2.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.show_boxstage2.Database.Models.MovieDetailsModel;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM  movieDetails")
    List<MovieDetailsModel> getAll();

    @Query("SELECT * FROM movieDetails where movieId = :id")
    MovieDetailsModel getMoviesByID(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(MovieDetailsModel... movieDetailsModel);

    @Delete
    void delete(MovieDetailsModel movieDetailsModel);

}
