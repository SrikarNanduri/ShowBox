package com.example.android.show_boxstage2.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.show_boxstage2.Adaptors.BookmarkListAdapter;
import com.example.android.show_boxstage2.Config.AppExecutors;
import com.example.android.show_boxstage2.Database.MovieDatabase;
import com.example.android.show_boxstage2.Database.MovieDetailsModel;
import com.example.android.show_boxstage2.R;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksActivity extends AppCompatActivity {
    private static final String TAG = BookmarksActivity.class.getSimpleName();
    private MovieDatabase mMovieDatabase;

    @BindView(R.id.API_Key_bookmark)
    TextView mTextView;
    @BindView(R.id.progressBar_bookmark)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar_bookmark)
    Toolbar mToolbar;
    @BindView(R.id.bookmark_display_iv)
    ImageView mImageView;
    @BindView(R.id.bookmarks_rv)
    RecyclerView mBookmarksRecyclerView;

    BookmarkListAdapter mBookmarkAdapter;
    int spanCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bookmarks");
        mProgressBar.setVisibility(View.GONE);
        mTextView.setText("No Bookmarks");

        mMovieDatabase = MovieDatabase.getInstance(getApplicationContext());
        Configuration newConfig = getResources().getConfiguration();
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 4;
        } else {
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                spanCount = 3;
            }
        }

        bookmarkRV();


    }

public void bookmarkRV(){
    mBookmarksRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
    mBookmarksRecyclerView.setHasFixedSize(true);
    BookmarksActivityViewModel viewModel = ViewModelProviders.of(this).get(BookmarksActivityViewModel.class);
    viewModel.getMovieDetais().observe(this, new Observer<List<MovieDetailsModel>>() {
        @Override
        public void onChanged(@Nullable List<MovieDetailsModel> movieDetailsModels) {
            Log.d(TAG, "Updating list of movies from livedata in Viewmodel");
            mBookmarkAdapter = new BookmarkListAdapter(BookmarksActivity.this, movieDetailsModels );
            mBookmarksRecyclerView.setAdapter(mBookmarkAdapter);
            if(movieDetailsModels.size() == 0) {
                mTextView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
            } else {
                mTextView.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
            }
        }
    });



}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
