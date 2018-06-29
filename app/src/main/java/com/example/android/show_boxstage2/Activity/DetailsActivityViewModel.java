package com.example.android.show_boxstage2.Activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.show_boxstage2.Database.MovieDetailsModel;

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
