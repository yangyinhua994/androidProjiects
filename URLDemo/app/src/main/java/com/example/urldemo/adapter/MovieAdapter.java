package com.example.urldemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.urldemo.R;
import com.example.urldemo.activity.VideoPlayerActivity;
import com.example.urldemo.dto.Movie;
import com.example.urldemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyinhua
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;
    private static final int TYPE_MOVIE = 0;
    private static final int TYPE_LOADING = 1;

    public MovieAdapter() {
        mMovies = new ArrayList<>();
    }

    public void setMovies(Context context, List<Movie> movies) {
        mContext = context;
        mMovies.addAll(movies);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getImageUrl())
                .into(holder.mImageView);
        holder.mMovieNameView.setText(movie.getTitle());
        holder.mMovieCategoriesView.setText(mContext.getResources().getString(R.string.categories) + getListString(movie.getCategories()));
        holder.mMovieDurationsView.setText(mContext.getResources().getString(R.string.duration) + movie.getDuration());
        holder.mMovieStarringView.setText(mContext.getResources().getString(R.string.starring)  + getListString(movie.getStarringList()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra("video_url", movie.getPlayUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mMovieNameView;
        TextView mMovieCategoriesView;
        TextView mMovieDurationsView;
        TextView mMovieStarringView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_movie);
            mMovieNameView = itemView.findViewById(R.id.text_movie_name);
            mMovieCategoriesView = itemView.findViewById(R.id.text_movie_categories);
            mMovieDurationsView = itemView.findViewById(R.id.text_movie_duration);
            mMovieStarringView = itemView.findViewById(R.id.text_movie_starring);
        }
    }

    private static String getListString(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String newS : list) {
            sb.append(newS).append("、");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

