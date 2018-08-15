package com.xsy.android.videoplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.goldplusgold.networklib.NetworkListener;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.adapter.RecommendMovieAdapter;
import com.xsy.android.videoplayer.apiservice.ApiService;
import com.xsy.android.videoplayer.model.Movies;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.BlurTransformation;


/**
 * 影片列表Fragment
 * <p/>
 * Created by woxingxiao on 2017-01-23.
 */
public class RecommendMovieListFragment extends Fragment implements DiscreteScrollView.ScrollStateChangeListener<RecommendMovieAdapter.ItemViewHolder> {

    @BindView(R.id.bg_img1)
    ImageView mBgImg1;
    @BindView(R.id.bg_img2)
    ImageView mBgImg2;
    @BindView(R.id.infinite_view_pager)
    DiscreteScrollView mInfiniteViewPager;

    private int mId;
    private boolean isIntentTriggered;
    private int mPreIntentPos;
    private int mPrePos;
    private RecommendMovieAdapter mAdapter;
    private BlurTransformation mBlurTransformation;
    private int mMaxIndex;

    public static RecommendMovieListFragment newInstance(int id) {
        RecommendMovieListFragment fragment = new RecommendMovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mId = getArguments().getInt("id");
        if (mId == 0) {
            getLastedMovie();
        } else {
            getClassicMovie();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        mInfiniteViewPager.setItemTransformer(
                new ScaleTransformer.Builder()
                        .setMinScale(0.8f)
                        .build()
        );
        mInfiniteViewPager.addScrollStateChangeListener(this);

        mBlurTransformation = new BlurTransformation(getActivity(), 10);
        mBgImg1.setAlpha(0f);
        mBgImg2.setImageResource(R.drawable.pic_bg_ocean);
        return view;
    }


    private void getLastedMovie() {
        ApiService.getInstance().getLastedMovie(new NetworkListener() {
            @Override
            public void onSuccess(String message) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }
                Movies movies = JSON.parseObject(message, Movies.class);
                mAdapter = new RecommendMovieAdapter(getContext());
                mAdapter.setArrayList(movies.getMovies());
                mInfiniteViewPager.setVisibility(View.VISIBLE);
                mInfiniteViewPager.setAdapter(mAdapter);
                mBgImg1.animate().alpha(1)
                        .setDuration(1000)
                        .withStartAction(new Runnable() {
                            @Override
                            public void run() {
                                displayBgImage(0, mBgImg1);
                            }
                        });
                mBgImg2.animate().alpha(0).setDuration(1000)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                displayBgImage(1, mBgImg2);
                            }
                        });

            }

            @Override
            public void onFail(String message) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getClassicMovie() {
        ApiService.getInstance().getClassicMovie(new NetworkListener() {
            @Override
            public void onSuccess(String message) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }
                Movies movies = JSON.parseObject(message, Movies.class);
                mAdapter = new RecommendMovieAdapter(getContext());
                mAdapter.setArrayList(movies.getMovies());
                mInfiniteViewPager.setVisibility(View.VISIBLE);
                mInfiniteViewPager.setAdapter(mAdapter);
                mBgImg1.animate().alpha(1)
                        .setDuration(1000)
                        .withStartAction(new Runnable() {
                            @Override
                            public void run() {
                                displayBgImage(0, mBgImg1);
                            }
                        });
                mBgImg2.animate().alpha(0).setDuration(1000)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                displayBgImage(1, mBgImg2);
                            }
                        });

            }

            @Override
            public void onFail(String message) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    private void displayBgImage(int index, ImageView imageView) {
        FragmentActivity activity = getActivity();
        if (activity != null) {

            Glide.with(activity)
                    .load(mAdapter.getArrayList().get(index).getImage())
                    .transform(mBlurTransformation)
                    .placeholder(R.drawable.pic_bg_ocean)
                    .error(R.drawable.pic_bg_ocean)
                    .crossFade()
                    .into(imageView);
        }
    }

    @Override
    public void onScrollStart(@NonNull RecommendMovieAdapter.ItemViewHolder currentItemHolder, int adapterPosition) {
        isIntentTriggered = true;
    }

    @Override
    public void onScrollEnd(@NonNull RecommendMovieAdapter.ItemViewHolder currentItemHolder, int adapterPosition) {
        mMaxIndex = adapterPosition > mMaxIndex ? adapterPosition : mMaxIndex;

        boolean isOdd = adapterPosition % 2 != 0;
        if (isOdd) {
            displayBgImage(adapterPosition, mBgImg2);

            if (mMaxIndex < adapterPosition + 1 && adapterPosition + 1 < mAdapter.getItemCount()) { // 预加载右边一张
                displayBgImage(adapterPosition + 1, mBgImg1);
            }
        } else {
            displayBgImage(adapterPosition, mBgImg1);

            if (mMaxIndex < adapterPosition + 1 && adapterPosition + 1 < mAdapter.getItemCount()) { // 预加载右边一张
                displayBgImage(adapterPosition + 1, mBgImg2);
            }
        }

        currentItemHolder.mItemTextViewName.setAlpha(1f);
        int size = mAdapter.getItemCount();
        if (mAdapter.getArrayList() != null && size > 0) {
            RecommendMovieAdapter.ItemViewHolder vh;

            if (adapterPosition == 0 && size > 1) {
                vh = (RecommendMovieAdapter.ItemViewHolder) mInfiniteViewPager.getViewHolder(adapterPosition + 1);
                if (vh != null) {
                    vh.mItemTextViewName.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mItemImageMovie.setVisibility(View.VISIBLE);
                    }
                }
            } else if (adapterPosition == size - 1 && size > 1) {
                vh = (RecommendMovieAdapter.ItemViewHolder) mInfiniteViewPager.getViewHolder(adapterPosition - 1);
                if (vh != null) {
                    vh.mItemTextViewName.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mItemImageMovie.setVisibility(View.VISIBLE);
                    }
                }
            } else if (adapterPosition > 0 && adapterPosition + 1 < size - 1) {
                vh = (RecommendMovieAdapter.ItemViewHolder) mInfiniteViewPager.getViewHolder(adapterPosition + 1);
                if (vh != null) {
                    vh.mItemTextViewName.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mItemImageMovie.setVisibility(View.VISIBLE);
                    }
                }
                vh = (RecommendMovieAdapter.ItemViewHolder) mInfiniteViewPager.getViewHolder(adapterPosition - 1);
                if (vh != null) {
                    vh.mItemTextViewName.setAlpha(0);

                    if (mPrePos != adapterPosition) {
                        vh.mItemImageMovie.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        mPrePos = adapterPosition;
    }

    @Override
    public void onScroll(float scrollPosition, @NonNull RecommendMovieAdapter.ItemViewHolder currentHolder, @NonNull RecommendMovieAdapter.ItemViewHolder newCurrent) {
        float position = Math.abs(scrollPosition);
        boolean isOdd = currentHolder.getAdapterPosition() % 2 != 0;
        int intentPos = newCurrent.getAdapterPosition();

        if (mPreIntentPos != intentPos) {
            isIntentTriggered = false;
        }
        if (isOdd) {
            if (!isIntentTriggered && intentPos >= 0 && intentPos <= mAdapter.getItemCount() - 1) {
                displayBgImage(intentPos, mBgImg1);

                isIntentTriggered = true;
            }

            mBgImg1.setAlpha(position);
            mBgImg2.setAlpha(1 - position);
        } else {
            if (!isIntentTriggered && intentPos >= 0 && intentPos <= mAdapter.getItemCount() - 1) {
                displayBgImage(intentPos, mBgImg2);

                isIntentTriggered = true;
            }

            mBgImg1.setAlpha(1 - position);
            mBgImg2.setAlpha(position);
        }
        mPreIntentPos = intentPos;

        float fastAlpha = position + 0.4f;
        currentHolder.mItemTextViewName.setAlpha(1 - fastAlpha);
        newCurrent.mItemTextViewName.setAlpha(fastAlpha);
    }

}
