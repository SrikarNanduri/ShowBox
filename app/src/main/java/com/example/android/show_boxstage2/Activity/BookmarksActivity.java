package com.example.android.show_boxstage2.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.show_boxstage2.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksActivity extends AppCompatActivity {
    @BindView(R.id.API_Key_bookmark)
    TextView mTextView;
    @BindView(R.id.progressBar_bookmark)
    ProgressBar mProgressBar;
    @BindView(R.id.bookmarks_rv)
    RecyclerView mBookmarksRV;
    @BindView(R.id.toolbar_bookmark)
    Toolbar mToolbar;


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


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
