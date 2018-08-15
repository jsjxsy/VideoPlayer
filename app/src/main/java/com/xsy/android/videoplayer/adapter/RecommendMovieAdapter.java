package com.xsy.android.videoplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldplusgold.support.lib.widget.recyclerview.adapter.RecyclerViewBaseAdapter;
import com.goldplusgold.support.lib.widget.recyclerview.listener.OnItemClickListener;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.activity.HomeActivity;
import com.xsy.android.videoplayer.activity.HomeDetailActivity;
import com.xsy.android.videoplayer.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/29.
 */

public class RecommendMovieAdapter extends RecyclerViewBaseAdapter<Movie> implements OnItemClickListener {
    public RecommendMovieAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_movie, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.setOnItemClickListener(this);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onItemClick(View view, int position) {
        Movie movie = getItem(position);
        if (movie == null) {
            return;
        }

        Intent intent = new Intent(mContext, HomeDetailActivity.class);
        intent.putExtra("url", movie.getUrl());
        intent.putExtra("type", HomeActivity.MOVIE_TYPE);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        return false;
    }


    public class ItemViewHolder extends ViewHolder {
        @BindView(R.id.movie_poster_img)
        public ImageView mItemImageMovie;
        @BindView(R.id.movie_name_text)
        public TextView mItemTextViewName;

        public ItemViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this, contentView);
        }

        @Override
        protected void createViewHolderAction(View convertView) {
        }

        @Override
        public void bindViewHolderAction(int position) {
            Movie movie = getItem(position);
            if (movie != null) {
                Glide.with(getContext()).load(movie.getImage()).into(mItemImageMovie);
                mItemTextViewName.setText(movie.getTitle());
            }

        }
    }

}
