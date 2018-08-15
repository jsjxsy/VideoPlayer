/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xsy.android.videoplayer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.goldplusgold.networklib.NetworkListener;
import com.goldplusgold.support.lib.widget.recyclerview.listener.OnItemClickListener;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.adapter.TextAdapter;
import com.xsy.android.videoplayer.apiservice.ApiService;
import com.xsy.android.videoplayer.model.Episode;
import com.xsy.android.videoplayer.model.Teleplay;

import java.util.ArrayList;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class TeleplayDetailFragment extends MovieDetailFragment implements OnItemClickListener {

    private RecyclerView mTeleplayList;
    private TextAdapter mTextAdapter;
    private TextView mTotal;
    Teleplay teleplay;


    public static TeleplayDetailFragment newInstance(String url) {
        TeleplayDetailFragment f = new TeleplayDetailFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }

    public TeleplayDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            teleplay = savedInstanceState.getParcelable("teleplay");
        }
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        mTotal = (TextView) v.findViewById(R.id.id_text_view_total);
        mTeleplayList = (RecyclerView) v.findViewById(R.id.id_teleplay_list);
        //GridLayout 3列
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 5);
        mTeleplayList.setLayoutManager(mgr);

        mTextAdapter = new TextAdapter(getActivity());
        mTeleplayList.setAdapter(mTextAdapter);
        mTextAdapter.setOnItemClickListener(this);
    }

    @Override
    public void requestNetwork(final View v) {
        if (teleplay != null) {
            bindDataAndView(v);
            return;
        }
        Bundle args = getArguments();
        if (args != null) {
            String url = args.getString("url");
            ApiService.getInstance().getDetail(url, new NetworkListener() {
                @Override
                public void onSuccess(String message) {
                    if (!isAvailableActivity()) {
                        return;
                    }
                    teleplay = JSON.parseObject(message, Teleplay.class);
                    bindDataAndView(v);
                }

                @Override
                public void onFail(String message) {
                    if (!isAvailableActivity()) {
                        return;
                    }
                    showToastMessage(message, Toast.LENGTH_LONG);
                }
            });
        }

    }

    private void bindDataAndView(View v) {
        if (teleplay != null) {
            String content = getString(R.string.total_episode);
            String total = String.format(content, String.valueOf(teleplay.getEpisodes()));
            mTotal.setText(total);
            ArrayList<Episode> episodes = teleplay.getTeleplay();
            mTextAdapter.setArrayList(episodes);
            int position = teleplay.getPosition();

            initPlayer(v, episodes.get(position).getUrl());
            int duration = teleplay.getCurrentDuration();
            player.seekTo(duration);

            mTitle.setText(episodes.get(position).getTitle());
            mRatingBar.setMax(10);

            String mark = teleplay.getMark();
            float score = 0;
            try {
                score = Float.parseFloat(mark);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRatingBar.setProgress((int) score);
            mMark.setText(String.valueOf(teleplay.getMark()));
            mType.setText(teleplay.getType());
            mDescription.setText(teleplay.getDescription());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_teleplay_detail;
    }

    @Override
    public void onItemClick(View view, int position) {
        Episode episode = mTextAdapter.getItem(position);
        if (episode == null) {
            return;
        }
        if (player != null) {
            player.play(episode.getUrl());
            teleplay.setPosition(position);
            mTitle.setText(episode.getTitle());
        }

    }

    @Override
    public void onStop() {
        if (player != null && teleplay != null) {
            teleplay.setCurrentDuration(player.getCurrentPosition());
        }
        super.onStop();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("teleplay", teleplay);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        return false;
    }
}
