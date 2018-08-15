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

package com.xsy.android.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.fragment.MovieDetailFragment;
import com.xsy.android.videoplayer.fragment.TeleplayDetailFragment;

import utils.ActivityUtils;


/**
 * Displays an add or edit task screen.
 */
public class HomeDetailActivity extends AppCompatActivity {


    private MovieDetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String url = "";
        int type = HomeActivity.MOVIE_TYPE;
        if (intent != null) {
            url = intent.getStringExtra("url");
            type = intent.getIntExtra("type", HomeActivity.MOVIE_TYPE);

        }
        detailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        switch (type) {
            case HomeActivity.MOVIE_TYPE:

                if (detailFragment == null) {
                    detailFragment = MovieDetailFragment.newInstance(url);
                }
                break;
            case HomeActivity.CARTOON_TYPE:
            case HomeActivity.VARIETY_TYPE:
            case HomeActivity.TELEPLAY_TYPE:
                if (detailFragment == null) {
                    detailFragment = TeleplayDetailFragment.newInstance(url);
                }
                break;
        }

        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                detailFragment, R.id.contentFrame);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (detailFragment != null) {
            detailFragment.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (detailFragment != null) {
            detailFragment.onBackPress();
        }
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
