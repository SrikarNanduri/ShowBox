package com.example.android.show_boxstage2.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM  movieDetails")
    LiveData<List<MovieDetailsModel>> getAll();

    @Query("SELECT * FROM movieDetails where movieId = :id")
   LiveData<MovieDetailsModel> getMoviesByID(String id);

    @Query("SELECT movieId FROM movieDetails where movieId = :id")
    String getID(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(MovieDetailsModel movieDetailsModel);

    @Delete
    void delete(MovieDetailsModel movieDetailsModel);

}
