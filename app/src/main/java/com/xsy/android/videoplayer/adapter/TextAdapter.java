package com.xsy.android.videoplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldplusgold.support.lib.widget.recyclerview.adapter.RecyclerViewBaseAdapter;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.model.Episode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/29.
 */

public class TextAdapter extends RecyclerViewBaseAdapter<Episode> {
    public TextAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_text_view, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setTag(holder);
        return holder;
    }


    public class ItemViewHolder extends ViewHolder {
        @BindView(R.id.id_text_item_episodes)
        TextView mItemEpisode;

        public ItemViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this, contentView);
        }

        @Override
        protected void createViewHolderAction(View convertView) {
        }

        @Override
        public void bindViewHolderAction(int position) {
            Episode episode = getItem(position);
            if (episode != null) {
                String content = mContext.getResources().getString(R.string.number_episode);
                String text = String.format(content, String.valueOf(episode.getEpisode()));
                mItemEpisode.setText(text);
            }

        }
    }

}
