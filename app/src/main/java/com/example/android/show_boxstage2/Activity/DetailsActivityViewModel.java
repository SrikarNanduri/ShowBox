package com.example.android.show_boxstage2.Activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.android.show_boxstage2.Database.Models.MovieDetailsModel;
import com.example.android.show_boxstage2.Database.MovieDatabase;

import java.util.List;

public class DetailsActivityViewModel extends ViewModel {

    private MutableLiveData<MovieDetailsModel> mMovieDetails;

    public void DetailActivityViewModel() {

        mMovieDetails = new MutableLiveData<>();
    }

    public MutableLiveData<MovieDetailsModel> getmMovieDetails() {
        return mMovieDetails;
    }

    public void setmMovieDetails(MutableLiveData<MovieDetailsModel> mMovieDetails) {
        this.mMovieDetails = mMovieDetails;
    }




}
