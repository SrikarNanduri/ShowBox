package com.example.android.show_boxstage2.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.show_boxstage2.Activity.DetailsActivity;
import com.example.android.show_boxstage2.Config.ConfigURL;
import com.example.android.show_boxstage2.Models.MovieDetails_POJO;
import com.example.android.show_boxstage2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarMovieListAdapter extends RecyclerView.Adapter<SimilarMovieListAdapter.SimilarMovieViewHolder> {

    Context context;
    private List<MovieDetails_POJO> mSimilarMovieList;
    private LayoutInflater mInflater;

    public SimilarMovieListAdapter(Context context, List<MovieDetails_POJO> mSimilarMovieList) {
        this.context = context;
        this.mSimilarMovieList = mSimilarMovieList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SimilarMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.similar_movies_list_item,parent,false);
        return new SimilarMovieViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimilarMovieViewHolder holder, final int position) {
        String imagePath = ConfigURL.POSTER_PATH + mSimilarMovieList.get(position).getPosterPath();
        Picasso.with(context).load(imagePath)
                .placeholder(R.drawable.ic_action_placeholder_white)
                .into(holder.similarMovieList);
        holder.similatTitle.setText(mSimilarMovieList.get(position).getTitle());

        holder.similarMovieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("movieList", mSimilarMovieList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSimilarMovieList.size();
    }

    class SimilarMovieViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.similar_movie_poster_iv)
        ImageView similarMovieList;
        @BindView(R.id.similar_title_tv)
        TextView similatTitle;


    public SimilarMovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}
