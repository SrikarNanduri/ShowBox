package com.example.android.show_boxstage2.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.show_boxstage2.Adaptors.CastListAdapter;
import com.example.android.show_boxstage2.Adaptors.ReviewListAdapter;
import com.example.android.show_boxstage2.Adaptors.SimilarMovieListAdapter;
import com.example.android.show_boxstage2.Adaptors.VideoListAdapter;
import com.example.android.show_boxstage2.Config.AppExecutors;
import com.example.android.show_boxstage2.Config.ConfigURL;
import com.example.android.show_boxstage2.Database.MovieDetailsModel;
import com.example.android.show_boxstage2.Database.MovieDatabase;
import com.example.android.show_boxstage2.Models.Cast;
import com.example.android.show_boxstage2.Models.Credits;
import com.example.android.show_boxstage2.Models.Genre_POJO;
import com.example.android.show_boxstage2.Models.MoreDetails;
import com.example.android.show_boxstage2.Models.MovieDetails_POJO;
import com.example.android.show_boxstage2.Models.MovieResponse;
import com.example.android.show_boxstage2.Models.Reviews;
import com.example.android.show_boxstage2.Models.Reviews_POJO;
import com.example.android.show_boxstage2.Models.Videos;
import com.example.android.show_boxstage2.Models.Videos_POJO;
import com.example.android.show_boxstage2.Network.ApiClient;
import com.example.android.show_boxstage2.Network.MovieData_Interface;
import com.example.android.show_boxstage2.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.example.android.show_boxstage2.BuildConfig.API_KEY;
import static com.example.android.show_boxstage2.Config.ConfigURL.BACKDROP_PATH;
import static com.example.android.show_boxstage2.Config.ConfigURL.CREDITS;
import static com.example.android.show_boxstage2.Config.ConfigURL.POSTER_PATH;
import static com.example.android.show_boxstage2.Config.ConfigURL.REVIEWS;
import static com.example.android.show_boxstage2.Config.ConfigURL.VIDEOS;
import static com.example.android.show_boxstage2.Config.ConfigURL.VIDEOS_PATH;


public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    int bookmarkCount = 1;
    private MovieDatabase mMovieDatabase;



    MovieDetails_POJO movie_details;
    MovieDetailsModel bookmarksList;

    List<Videos_POJO> videosList;
    List<Cast> castList;
    List<Reviews_POJO> reviewsList;
    List<Genre_POJO> genreList;

    @BindView(R.id.similar_tv)
    TextView similarMoviesHeadline;

    @BindView(R.id.details_activity_sv)
    ScrollView mScrollView;
    @BindView(R.id.movie_poster_iv) ImageView poster;
    @BindView(R.id.bookmark_border_iv) ImageView bookmark;
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.synopsis_tv) TextView synopsis;
    @BindView(R.id.rating_tv) TextView rating;
    @BindView(R.id.release_tv) TextView release;
    @BindView(R.id.runtime_tv) TextView runtime;
    @BindView(R.id.tagline_tv) TextView tagline;
    @BindView(R.id.status_tv) TextView status;
    @BindView(R.id.genres_tv) TextView genres;
    @BindView(R.id.genres_type_tv) TextView genres_types;
    @BindView(R.id.backdrop_ll) LinearLayout backdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.review_tv)
    TextView mReviewTV;
    @BindView(R.id.share_ib)
    ImageButton mShare;
    @BindView(R.id.trailers_tv)
    TextView mTrailersTV;


    @BindView(R.id.video_rv)
    RecyclerView mVideoRecyclerView;
    @BindView(R.id.cast_rv)
    RecyclerView mCastRecyclerView;
    @BindView(R.id.review_rv)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.similar_rv)
    RecyclerView mSimilarRecyclerView;

    VideoListAdapter mVideoAdapter;
    CastListAdapter mCastAdapter;
    ReviewListAdapter mReviewAdapter;
    SimilarMovieListAdapter mSimilarAdapter;




    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mMovieDatabase = MovieDatabase.getInstance(getApplicationContext());


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if(getIntent().getParcelableExtra("movieList") != null) {
            movie_details = getIntent().getParcelableExtra("movieList");
            movieListDetails_helper();
        } else if(getIntent().getParcelableExtra("bookmarkMovieList") != null){
            bookmarksList = getIntent().getParcelableExtra("bookmarkMovieList");
            bookmarkHelper();
        }
    }

    public void bookmarkHelper(){
        final String movieId = bookmarksList.getMovieId();
        final String movieTitle = bookmarksList.getTitle();
        final String moviePoster = bookmarksList.getPosterPath();
        final String movieBackdrop = bookmarksList.getBackdrop_path();
        final String movieReleaseDate = bookmarksList.getReleaseDate();
        final String movieRating = bookmarksList.getVoteAverage();
        final String movieRuntime = bookmarksList.getRuntime();
        final String movieStatus = bookmarksList.getStatus();
        final String movieTagLine = bookmarksList.getTagline();
        final String movieSynopsis = bookmarksList.getOverview();
        final List<Genre_POJO> movieGenres = bookmarksList.getGenres();
        final List<Videos_POJO> movieTrailers = bookmarksList.getVideos();
        final List<Cast> movieCast = bookmarksList.getCast();
        final List<Reviews_POJO> movieReviews = bookmarksList.getReviews();

        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        poster.setTransitionName("bookmarkPoster");

        Picasso.with(this).load( moviePoster)
                .into(poster);

        Picasso.with(this).load( movieBackdrop)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        backdrop.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputDate = null;
        try {
            Date date = inputFormat.parse(movieReleaseDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        synopsis.setText(movieSynopsis);
        synopsis.setEllipsize(TextUtils.TruncateAt.END);
        synopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(synopsis.getMaxLines() == 4){
                    synopsis.setEllipsize(null);
                    synopsis.setMaxLines(100);
                } else {
                    if(synopsis.getMaxLines() == 100){
                        synopsis.setEllipsize(TextUtils.TruncateAt.END);
                        synopsis.setMaxLines(4);
                    }
                }
            }
        });
        rating.setText(movieRating);
        release.setText(outputDate);
        title.setText(movieTitle);
        runtime.setText(movieRuntime);
        tagline.setText(movieTagLine);
        status.setText(movieStatus);

        String genre = "";
        for(int i = 0; i< movieGenres.size()-1 ; i++){
            genre += movieGenres.get(i).getName() + ", ";
        }
        genre += movieGenres.get(movieGenres.size()-1).getName();

        genres_types.setText(genre);
        if(movieTrailers != null){
            videoRV(movieTrailers);
        }

        if(movieCast != null){
            castRV(movieCast);
        }

        if(movieReviews != null){
            reviewRV(movieReviews);
        }else {
            String reviewDetails = "No Reviews Available";
            mReviewTV.setText(reviewDetails);
        }

        bookmark.setImageResource(R.drawable.ic_action_bookmark_white);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark.setImageResource(R.drawable.ic_action_bookmark_white_border);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mMovieDatabase.moviesDao().delete(bookmarksList);
                        Log.v("Database deleted", bookmarksList.toString());
                        getWindow().setSharedElementReturnTransition(null);
                        getWindow().setSharedElementReenterTransition(null);
                        poster.setTransitionName(null);
                    }
                });
            }
        });
    }

    public void movieListDetails_helper ()
    {
        String title_value = movie_details.getTitle();
        String poster_path = movie_details.getPosterPath();
        String plot_synopsis = movie_details.getOverview();
        String user_rating = movie_details.getVoteAverage();
        String release_date = movie_details.getReleaseDate();
        final String backdrop_path = movie_details.getBackdrop_path();


        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        poster.setTransitionName("poster");

        Picasso.with(this).load( ConfigURL.POSTER_PATH + poster_path)
                .into(poster);

        Picasso.with(this).load( ConfigURL.BACKDROP_PATH + backdrop_path)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        backdrop.setBackground(new BitmapDrawable(getApplication().getResources(),bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        backdrop.setBackgroundDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputDate = null;
        try {
            Date date = inputFormat.parse(release_date);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        synopsis.setText(plot_synopsis);
        synopsis.setEllipsize(TextUtils.TruncateAt.END);
        synopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(synopsis.getMaxLines() == 4){
                    synopsis.setEllipsize(null);
                    synopsis.setMaxLines(100);
                } else {
                    if(synopsis.getMaxLines() == 100){
                        synopsis.setEllipsize(TextUtils.TruncateAt.END);
                        synopsis.setMaxLines(4);
                    }
                }
            }
        });
        rating.setText(user_rating);
        release.setText(outputDate);
        title.setText(title_value);

        network_helper(movie_details.getid());

    }

    public void onBookmarkSelected(){
        final String movieId = movie_details.getid();
        final String movieTitle = movie_details.getTitle();
        final String moviePoster = POSTER_PATH + movie_details.getPosterPath();
        final String movieBackDrop = BACKDROP_PATH + movie_details.getBackdrop_path();
        final String movieReleaseDate = movie_details.getReleaseDate();
        final String movieRating = movie_details.getVoteAverage();
        final String movieRunTime = runtime.getText().toString();
        final String movieStatus = status.getText().toString();
        final String movieTagline = tagline.getText().toString();
        final String movieSynopsis = synopsis.getText().toString();
        final List<Videos_POJO> movieTrailer = getTrailers();
        final List<Cast> movieCast = getCast();
        final List<Reviews_POJO> movieReview = getReview();
        final List<Genre_POJO> movieGenre = getGenre();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final String movieDetailId = mMovieDatabase.moviesDao().getID(movieId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(movieDetailId != null){
                            bookmark.setImageResource(R.drawable.ic_action_bookmark_white);
                            bookmarkCount = 2;
                        }
                    }
                });

            }
        });

        //System.out.println(movieId + movieTitle + moviePoster+ movieSynopsis+ movieRating+ movieReleaseDate+ movieBackDrop+ movieStatus+movieRunTime+ movieTagline+ movieGenre+ movieTrailer+ movieCast+ movieReview );
        Log.v("bookmarkcount", String.valueOf(bookmarkCount));
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MovieDetailsModel movieDetailsModel = new MovieDetailsModel(movieId, movieTitle, moviePoster, movieSynopsis, movieRating, movieReleaseDate, movieBackDrop, movieStatus,movieRunTime, movieTagline, movieGenre, movieTrailer, movieCast, movieReview );
                if (bookmarkCount == 1) {
                    bookmark.setImageResource(R.drawable.ic_action_bookmark_white);
                    bookmarkCount = 2;
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mMovieDatabase.moviesDao().Insert(movieDetailsModel);
                            Log.v("Database saved", movieDetailsModel.getTitle());
                           Log.v("Saved list", mMovieDatabase.moviesDao().getMoviesByID(movieId).toString());
                        }
                    });

                } else if (bookmarkCount == 2) {
                    bookmark.setImageResource(R.drawable.ic_action_bookmark_white_border);
                    bookmarkCount = 1;
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mMovieDatabase.moviesDao().delete(movieDetailsModel);
                            Log.v("Database deleted", movieDetailsModel.toString());
                        }
                    });

                }
            }
        });
    }


    private void videoRV(List<Videos_POJO> trailers){
        mVideoAdapter = new VideoListAdapter(this, trailers);
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false));
        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
        setTrailers(trailers);
    }

    private void setTrailers(List<Videos_POJO> trailers){
        videosList = trailers;

    }
    private List<Videos_POJO> getTrailers(){
        return videosList;
    }

    private void castRV(List<Cast> cast){
        mCastAdapter = new CastListAdapter(this, cast);
        mCastRecyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false));
        mCastRecyclerView.setHasFixedSize(true);
        mCastRecyclerView.setAdapter(mCastAdapter);
        setCast(cast);
    }

    private void setCast(List<Cast> cast){
        castList = cast;

    }
    private List<Cast> getCast(){
        return castList;
    }


    private void reviewRV(List<Reviews_POJO> reviews){
        mReviewAdapter = new ReviewListAdapter(this, reviews);
        mReviewRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewRecyclerView.setNestedScrollingEnabled(false);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        setReview(reviews);
    }

    private void setReview(List<Reviews_POJO> reviews){
        reviewsList = reviews;

    }
    private List<Reviews_POJO> getReview(){
        return reviewsList;
    }

    private void similarRV(List<MovieDetails_POJO> similar){
        mSimilarAdapter = new SimilarMovieListAdapter(this, similar);
        mSimilarRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,false));
        mSimilarRecyclerView.setHasFixedSize(true);
        mSimilarRecyclerView.setAdapter(mSimilarAdapter);
    }


//This is the similar movies recyclerview method helper
    private void similarMovie_network_helper(String id){
        MovieData_Interface apiService = ApiClient.getClient().create(MovieData_Interface.class);
        Call<MovieResponse> call = apiService.getSimilarMovies(id, API_KEY);
        Log.v("url of similar details", call.request().url() + "");
        Log.v("id",id);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MovieDetails_POJO> similarMovies = response.body().getResults();
                        if(similarMovies.size() !=0) {
                            similarMoviesHeadline.setVisibility(View.VISIBLE);
                            similarRV(similarMovies);
                            Log.d(TAG, "number of similar movies received:" + similarMovies.size());
                        } else {
                            similarMoviesHeadline.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    private void network_helper(final String id){
        String queries = VIDEOS+","+CREDITS+","+REVIEWS;

        MovieData_Interface apiService = ApiClient.getClient().create(MovieData_Interface.class);
        Call<MoreDetails> call = apiService.getMoreDetails(id, API_KEY, queries);
        Log.v("url of more details", call.request().url() + "");
        Log.v("id", id);
        call.enqueue(new Callback<MoreDetails>() {
            @Override
            public void onResponse(Call<MoreDetails> call, Response<MoreDetails> response) {
                if(response.isSuccessful()){
                    if( response.body() != null) {
                        List<Genre_POJO> moreDetails = response.body().getGenres();
                        setGenre(moreDetails);
                        String runTime = response.body().getRuntime();
                        String tagLine = response.body().getTagline();
                        String movieStatus = response.body().getStatus();
                        final Videos videos = response.body().getVideos();
                        mShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_STREAM, "Show Box");
                                if(videos.getResults().size() != 0) {
                                    String link = "Check out the trailer of " + movie_details.getTitle() + ": ";
                                    link = link + VIDEOS_PATH + videos.getResults().get(0).getKey();
                                    intent.putExtra(Intent.EXTRA_TEXT, link);
                                    startActivity(Intent.createChooser(intent, "Share Image"));
                                } else {
                                    Toast.makeText(DetailsActivity.this, "No Links Available", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        if(videos.getResults().size() != 0) {
                            List<Videos_POJO> trailers = videos.getResults();
                            // A recycler view to set trailers
                            Log.v("Video thumbnail URL", "https://img.youtube.com/vi/" + trailers.get(0).getKey() + "/0.jpg");
                            videoRV(trailers);
                        } else {
                            String trailerDetails = "No Trailers Available";
                            mTrailersTV.setText(trailerDetails);
                        }

                        Credits credits = response.body().getCredits();
                        if(credits.getCast().size() != 0) {
                            List<Cast> cast = credits.getCast();
                            // A recycler view to set cast
                            Log.v("cast thumbnail URL", POSTER_PATH + cast.get(0).getProfilePath());
                            castRV(cast);
                        }


                        Reviews reviews = response.body().getReviews();
                        if(reviews.getResults().size() != 0) {
                            List<Reviews_POJO> userReviews = reviews.getResults();
                            // A recycler view to set userReviews
                            Log.v("reviews author", userReviews.get(0).getAuthor());
                            reviewRV(userReviews);
                        } else {
                            String reviewDetails = "No Reviews Available";
                            mReviewTV.setText(reviewDetails);
                        }

                        String runtimeInMins = runTime + getString(R.string.mins);
                        runtime.setText(runtimeInMins);
                        tagline.setText(tagLine);
                        status.setText(movieStatus);

                        if(moreDetails.size() != 0) {
                            String genre = "";
                            for (int i = 0; i < moreDetails.size() - 1; i++) {
                                genre += moreDetails.get(i).getName() + ", ";
                            }
                            genre += moreDetails.get(moreDetails.size() - 1).getName();

                            genres_types.setText(genre);
                        } else {
                            String genreDetails = "No Genre Available";
                            genres.setText(genreDetails);
                        }
                    }
                    similarMovie_network_helper(id);
                    onBookmarkSelected();
                }
            }

            @Override
            public void onFailure(Call<MoreDetails> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void setGenre(List<Genre_POJO> genre){
        genreList = genre;

    }
    private List<Genre_POJO> getGenre(){
        return genreList;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
