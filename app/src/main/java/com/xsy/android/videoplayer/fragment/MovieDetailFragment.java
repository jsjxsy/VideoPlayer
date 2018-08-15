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


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.goldplusgold.networklib.NetworkListener;
import com.goldplusgold.support.lib.base.fragment.BaseFragment;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.apiservice.ApiService;
import com.xsy.android.videoplayer.model.Movie;
import com.xsy.android.videoplayer.util.PlayerManager;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class MovieDetailFragment extends BaseFragment implements View.OnClickListener, PlayerManager.PlayerStateListener, Handler.Callback {

    protected PlayerManager player;
    protected View mProgressView;
    protected ImageView mImageViewPlay;
    protected TextView mCurrentTime;
    protected SeekBar mSeekBar;
    protected TextView mTotalTime;
    protected ImageView mZoom;
    protected View mControlLayout;
    protected RatingBar mRatingBar;
    Handler mHandler = new Handler(this);
    public static final int REFRESH_CONTROL_MESSAGE = 0x01;
    protected TextView mMark;
    protected TextView mType;
    protected TextView mDescription;
    protected TextView mTitle;
    private Movie movie;
    Configuration newConfig;


    public static MovieDetailFragment newInstance(String url) {
        MovieDetailFragment f = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        super.initView();
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Override
    protected void initView(final View v) {
        super.initView(v);
        initHead(v);
        initFoot(v);
        requestNetwork(v);
    }


    public void initHead(View v) {
        mTitle = (TextView) v.findViewById(R.id.id_title_text_view);
        mProgressView = v.findViewById(R.id.vodplayer_buffer_view);
        mControlLayout = v.findViewById(R.id.id_control_relativeLayout);
        mImageViewPlay = (ImageView) v.findViewById(R.id.id_image_view_play);
        mImageViewPlay.setOnClickListener(this);
        mCurrentTime = (TextView) v.findViewById(R.id.vodplayer_time_label_curr);
        mSeekBar = (SeekBar) v.findViewById(R.id.vodplayer_seek_bar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mTotalTime = (TextView) v.findViewById(R.id.vodplayer_time_label_total);
        mZoom = (ImageView) v.findViewById(R.id.id_image_view_zoom);
        mZoom.setOnClickListener(this);
    }

    public void initFoot(View v) {
        mRatingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        mMark = (TextView) v.findViewById(R.id.id_mark_movie);
        mType = (TextView) v.findViewById(R.id.id_type_movie);
        mDescription = (TextView) v.findViewById(R.id.id_text_view_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            newConfig = savedInstanceState.getParcelable("newConfig");
            movie = savedInstanceState.getParcelable("movie");
        }
    }

    public void requestNetwork(final View v) {
        if (movie != null) {
            bindDataAndView(v);
            player.seekTo(movie.getDuration());
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
                    movie = JSON.parseObject(message, Movie.class);
                    bindDataAndView(v);
                    player.seekTo(movie.getDuration());
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_detail;
    }

    public void initPlayer(View v, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        player = new PlayerManager(getActivity(), v);
        if (newConfig != null && newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            player.setVideoViewFullScreen(true);
            player.playInFullScreen(true);
            mZoom.setImageResource(R.drawable.ic_player_zoom_out);
            player.setFullScreenOnly(true);
        } else {
            player.setVideoViewFullScreen(false);
            player.playInFullScreen(false);
            mZoom.setImageResource(R.drawable.ic_palyer_zoom_in);
            player.setFullScreenOnly(false);
        }

        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        player.setPlayerStateListener(this);
        player.play(url);
    }


    private void bindDataAndView(View v) {
        if (movie != null) {
            initPlayer(v, movie.getUrl());
            mTitle.setText(movie.getTitle());
            mRatingBar.setMax(10);
            String mark = movie.getMark();
            float score = 0;
            try {
                score = Float.parseFloat(mark);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRatingBar.setProgress((int) score);
            mMark.setText(String.valueOf(movie.getMark()));
            mType.setText(movie.getType());
            mDescription.setText(movie.getDescription());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            if (movie != null) {
                movie.setDuration(player.getCurrentPosition());
            }
            player.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        mHandler.removeCallbacksAndMessages(null);
    }


    public void onBackPress() {
        if (player != null) {
            player.onBackPressed();
        }
    }

    public void onTouchEvent(MotionEvent event) {
        if (player != null) {
            player.gestureDetector.onTouchEvent(event);
            if (mControlLayout.getVisibility() == View.VISIBLE) {
                mControlLayout.setVisibility(View.GONE);
            } else {
                mControlLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onComplete() {
        if (player == null) {
            return;
        }
        if (player.getFullScreenOlny()) {
            mZoom.setImageResource(R.drawable.ic_palyer_zoom_in);
            player.setFullScreenOnly(false);
        }
    }

    @Override
    public void onError() {
        showToastMessage("视频加载失败！", Toast.LENGTH_LONG);
    }

    @Override
    public void onLoading() {
        mProgressView.setVisibility(View.VISIBLE);
        mControlLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPlay() {
        mProgressView.setVisibility(View.GONE);
        mControlLayout.setVisibility(View.VISIBLE);

        mSeekBar.setMax(player.getDuration());


        String totalTime = player.generateTime(player.getDuration());
        mTotalTime.setText(totalTime);

        setProgress();

        mHandler.sendEmptyMessageDelayed(REFRESH_CONTROL_MESSAGE, 1000);
    }


    void setProgress() {
        mSeekBar.setProgress(player.getCurrentPosition());
        String currentTime = player.generateTime(player.getCurrentPosition());
        mCurrentTime.setText(currentTime);
    }

    @Override
    public void onClick(View v) {
        if (player == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.id_image_view_play:
                if (player.isPlaying()) {
                    mImageViewPlay.setImageResource(R.drawable.player_play_can_play);
                    player.pause();
                } else {
                    mImageViewPlay.setImageResource(R.drawable.player_play_can_pause);
                    player.start();
                }
                break;
            case R.id.id_image_view_zoom:
                if (!player.getFullScreenOlny()) {
                    mZoom.setImageResource(R.drawable.ic_player_zoom_out);
                    player.setFullScreenOnly(true);
                } else {
                    mZoom.setImageResource(R.drawable.ic_palyer_zoom_in);
                    player.setFullScreenOnly(false);
                }
                break;

        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case REFRESH_CONTROL_MESSAGE:
                setProgress();
                mHandler.sendEmptyMessageDelayed(REFRESH_CONTROL_MESSAGE, 1000);
                break;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("newConfig", newConfig);
        outState.putParcelable("movie", movie);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.newConfig = newConfig;
    }
}
