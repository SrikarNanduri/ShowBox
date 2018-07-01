package com.example.android.show_boxstage2.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    MovieDetailsModel movie_details;
    private MovieDatabase mMovieDatabase;

    @BindView(R.id.API_Key_bookmark)
    TextView mTextView;
    @BindView(R.id.progressBar_bookmark)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar_bookmark)
    Toolbar mToolbar;

    @BindView(R.id.bookmarks_rv)
    RecyclerView mBookmarksRecyclerView;

    BookmarkListAdapter mBookmarkAdapter;

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
        /*BookmarksActivityViewModel viewModel = ViewModelProviders.of(this).get(BookmarksActivityViewModel.class);
       // Log.v("Database data", moviesDetailsBookmark.getValue().get(0).getTagline());

        viewModel.getMovieDetais().observe(this, new Observer<List<MovieDetailsModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieDetailsModel> movieDetailsModels) {
                mBookmarkAdapter = new BookmarkListAdapter(getApplicationContext(), movieDetailsModels);
                Log.v(TAG,"Receiving database update here in view model");
            }
        });*/
        mBookmarksRecyclerView
                .setLayoutManager(new GridLayoutManager(this, 2));
        mBookmarksRecyclerView.setHasFixedSize(true);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<MovieDetailsModel> movieDetails = mMovieDatabase.moviesDao().getAll();
                Log.v("Bookmarks page", movieDetails.get(0).getTitle());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBookmarkAdapter = new BookmarkListAdapter(getApplicationContext(), movieDetails );
                        mBookmarksRecyclerView.setAdapter(mBookmarkAdapter);
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //mBookmarkAdapter.setMovieDetails(mMovieDatabase.moviesDao().getAll());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
