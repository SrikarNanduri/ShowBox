package com.example.android.show_boxstage2.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.show_boxstage2.Activity.DetailsActivity;
import com.example.android.show_boxstage2.Config.ConfigURL;
import com.example.android.show_boxstage2.Database.MovieDetailsModel;
import com.example.android.show_boxstage2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.BookmarkViewHolder>{

    Context context;
    private List<MovieDetailsModel> mMovieList;
    private LayoutInflater mInflater;

    public BookmarkListAdapter(Context context, List<MovieDetailsModel> mMovieList) {
        this.context = context;
        this.mMovieList = mMovieList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movie_list_item,parent,false);
        return new BookmarkViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookmarkViewHolder holder, final int position) {
        Picasso.with(context).load(mMovieList.get(position).getPosterPath())
                .placeholder(R.drawable.ic_action_movie_placeholder)
                .into(holder.movieList);
        holder.title.setText(mMovieList.get(position).getTitle());
        holder.movieList.setTransitionName("bookmarkPoster");

        holder.movieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("bookmarkMovieList", mMovieList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.movieList, "bookmarkPoster");
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class BookmarkViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.movie_poster_iv)
        ImageView movieList;
        @BindView(R.id.home_title_tv)
        TextView title;

        public BookmarkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setMovieDetails(List<MovieDetailsModel> movieList){
        mMovieList = movieList;
    }

    public void clear(){
        mMovieList.clear();
        notifyDataSetChanged();
    }
}
