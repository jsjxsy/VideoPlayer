package com.xsy.android.videoplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goldplusgold.support.lib.widget.recyclerview.adapter.RecyclerViewBaseAdapter;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.model.Image;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/29.
 */

public class ImageAdapter extends RecyclerViewBaseAdapter<Image> {
    public ImageAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_image, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setTag(holder);
        return holder;
    }


    public class ItemViewHolder extends ViewHolder {
        @BindView(R.id.id_img_item_movie)
        ImageView mItemImageMovie;

        public ItemViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this, contentView);
        }

        @Override
        protected void createViewHolderAction(View convertView) {
        }

        @Override
        public void bindViewHolderAction(int position) {
            Image image = getItem(position);
            if (image != null) {
                Glide.with(mContext)
                        .load(image.getImage())
                        .asBitmap()//强制Glide返回一个Bitmap对象
                        .into(mItemImageMovie);


            }

        }
    }

}
