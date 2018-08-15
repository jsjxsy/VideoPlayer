package com.xsy.android.videoplayer.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.goldplusgold.support.lib.base.activity.CheckPermissionsActivity;
import com.goldplusgold.support.lib.utils.ScreenSizeUtil;
import com.goldplusgold.support.lib.widget.scroller.ViewPagerNoScroll;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.adapter.TabPagerAdapter;
import com.xsy.android.videoplayer.fragment.RecommendMovieListFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 首页
 * <p/>
 * Created by woxingxiao on 2017-01-25.
 */
public class RecommendActivity extends CheckPermissionsActivity {

    @BindView(R.id.view_pager)
    ViewPagerNoScroll mViewPager;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.now_radio)
    RadioButton mNowBtn;
    @BindView(R.id.coming_radio)
    RadioButton mComingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transparentStatusBar();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resId);

        FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        lp1.topMargin = statusBarHeight;
        toolbar.setLayoutParams(lp1);

        FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) mRadioGroup.getLayoutParams();
        lp2.topMargin = ScreenSizeUtil.dip2px(this, 56) + statusBarHeight;
        mRadioGroup.setLayoutParams(lp2);


        RecommendMovieListFragment[] fragments = new RecommendMovieListFragment[2];
        fragments[0] = RecommendMovieListFragment.newInstance(0);
        fragments[1] = RecommendMovieListFragment.newInstance(1);

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), this);
        adapter.setArrayList(new ArrayList(Arrays.asList(fragments)));
        adapter.setTabTitles(new String[]{getString(R.string.title_movie_lasted), getString(R.string.title_movie_classic)});
        mViewPager.setAdapter(adapter);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int position = checkedId == R.id.now_radio ? 0 : 1;
                if (mViewPager.getCurrentItem() == position)
                    return;

                mViewPager.setCurrentItem(position);
            }
        });
        String font = "font.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), font);
        mNowBtn.setTypeface(typeface);
        mComingBtn.setTypeface(typeface);

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
