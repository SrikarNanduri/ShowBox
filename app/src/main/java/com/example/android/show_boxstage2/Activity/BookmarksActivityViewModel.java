package com.example.android.show_boxstage2.Activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.show_boxstage2.Database.MovieDatabase;
import com.example.android.show_boxstage2.Database.MovieDetailsModel;

import java.util.List;

public class BookmarksActivityViewModel extends AndroidViewModel {

    private static final String TAG = BookmarksActivityViewModel.class.getSimpleName();

    private LiveData<List<MovieDetailsModel>> movieDetais;


    public BookmarksActivityViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movieDetais = database.moviesDao().getAll();
    }

    public LiveData<List<MovieDetailsModel>> getMovieDetais() {
        return movieDetais;
    }
}
