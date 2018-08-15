package com.xsy.android.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldplusgold.support.lib.base.activity.BaseActivity;
import com.goldplusgold.support.lib.utils.AppCacheSharedPreferences;
import com.goldplusgold.support.lib.widget.pageindicator.CirclePageIndicator;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.adapter.GuideViewAdapter;
import com.xsy.android.videoplayer.util.SharedPreferencesConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by admin on 2017/9/11.
 */

public class GuideActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.dot_marks)
    CirclePageIndicator mDotMarks;
    @BindView(R.id.id_button_enter)
    Button mEnter;
    @BindView(R.id.id_guide_content)
    TextView mContent;
    private int[] imageView = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private List<View> list;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        boolean isFirst = AppCacheSharedPreferences.getCacheBoolean(SharedPreferencesConstant.IS_FIRST_LOGIN, true);
        if (!isFirst) {
            Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//
        super.initView();
        addView();
        //定义一个指示变量，即布局中定义的那个
        mDotMarks.setViewPager(mViewPager);
        mContent.setText("免费看VIP电影、电视剧、动漫");
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mContent.setText("免费看VIP电影、电视剧、动漫");
                    mEnter.setVisibility(View.GONE);
                } else if (position == 1) {
                    mContent.setText("视频播放无广告,节约宝贵时间");
                    mEnter.setVisibility(View.GONE);
                } else if (position == 2) {
                    mContent.setText("只看好电影、电视剧、动漫、综艺!");
                    mEnter.setVisibility(View.VISIBLE);
                    mEnter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppCacheSharedPreferences.putCacheBoolean(SharedPreferencesConstant.IS_FIRST_LOGIN, false);
                            Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void addView() {
        list = new ArrayList<>();
        // 将imageview添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        for (int anImageView : imageView) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(anImageView);
            list.add(iv);
        }
        // 加入适配器
        mViewPager.setAdapter(new GuideViewAdapter(list));
    }
}
