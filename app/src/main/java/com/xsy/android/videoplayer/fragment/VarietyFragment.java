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

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.goldplusgold.networklib.NetworkListener;
import com.goldplusgold.support.lib.base.fragment.BaseFragment;
import com.goldplusgold.support.lib.widget.recyclerview.DividerGridViewItemDecoration;
import com.goldplusgold.support.lib.widget.recyclerview.RecyclerViewExtended;
import com.goldplusgold.support.lib.widget.recyclerview.listener.OnItemClickListener;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.activity.HomeActivity;
import com.xsy.android.videoplayer.activity.HomeDetailActivity;
import com.xsy.android.videoplayer.adapter.VarietyAdapter;
import com.xsy.android.videoplayer.apiservice.ApiService;
import com.xsy.android.videoplayer.model.Varieties;
import com.xsy.android.videoplayer.model.Variety;

import butterknife.BindView;
import butterknife.OnClick;

public class VarietyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewExtended.OnLoadMoreListener, OnItemClickListener {


    @BindView(R.id.id_view_stub_network_unavailable_display)
    ViewStub mNetworkUnavailableDisplay;
    @BindView(R.id.recycler_view)
    RecyclerViewExtended mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.id_empty_view_single_type_fragment_notification)
    LinearLayout mEmptyView;
    private VarietyAdapter mMainAdapter;

    public static VarietyFragment newInstance() {
        VarietyFragment fragment = new VarietyFragment();
        return fragment;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerGridViewItemDecoration(getActivity()));
        mRecyclerView.setOnLoadMoreListener(this);

        mMainAdapter = new VarietyAdapter(getActivity());
        mRecyclerView.setAdapter(mMainAdapter);
        mMainAdapter.setOnItemClickListener(this);
        sendVarietyRequest();

    }


    private void sendVarietyRequest() {
        ApiService.getInstance().getVariety(new NetworkListener() {
            @Override
            public void onSuccess(String message) {
                if (!isAvailableActivity()) {
                    return;
                }
                mRefreshLayout.setRefreshing(false);
                Varieties varieties = JSON.parseObject(message, Varieties.class);
                mMainAdapter.setArrayList(varieties.getVarieties());
                if (mMainAdapter.getItemCount() < 1) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mRefreshLayout.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.VISIBLE);
                }
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


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }


    @OnClick(R.id.id_empty_view_single_type_fragment_notification)
    public void onViewClicked() {
    }

    @Override
    public void onRefresh() {
        sendVarietyRequest();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(View view, int position) {
        Variety variety = mMainAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
        intent.putExtra("url", variety.getUrl());
        intent.putExtra("type", HomeActivity.VARIETY_TYPE);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        return false;
    }
}
