package com.xsy.android.videoplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldplusgold.support.lib.widget.recyclerview.adapter.RecyclerViewBaseAdapter;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.model.Teleplay;
import com.xsy.android.videoplayer.model.Variety;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/29.
 */

public class VarietyAdapter extends RecyclerViewBaseAdapter<Variety> {
    public VarietyAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.layout_item_main_home, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setTag(holder);
        return holder;
    }


    public class ItemViewHolder extends ViewHolder {
        @BindView(R.id.id_img_item_movie)
        ImageView mItemImageMovie;
        @BindView(R.id.id_text_view_mark)
        TextView mItemTextViewMark;
        @BindView(R.id.id_text_view_movie_type)
        TextView mItemTextViewMovieType;
        @BindView(R.id.id_name_item_movie)
        TextView mItemMovieName;
        @BindView(R.id.id_description_item_movie)
        TextView mItemMovieDescription;

        public ItemViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this, contentView);
        }

        @Override
        protected void createViewHolderAction(View convertView) {
        }

        @Override
        public void bindViewHolderAction(int position) {
            Variety variety = getItem(position);
            if (variety != null) {
                Glide.with(getContext()).load(variety.getImage()).into(mItemImageMovie);
                mItemTextViewMark.setText(variety.getMark());
                mItemTextViewMovieType.setText(variety.getType());
                mItemMovieName.setText(variety.getTitle());
                mItemMovieDescription.setText(variety.getDescription());
            }

        }
    }

}
