package com.filemanager.lib.activity;

import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.filemanager.lib.R;
import com.filemanager.lib.adapter.TabPagerAdapter;
import com.filemanager.lib.bean.FileDao;
import com.filemanager.lib.fragment.AudioFragment;
import com.filemanager.lib.fragment.VideoFragment;
import com.filemanager.lib.utils.FileUtil;
import com.goldplusgold.support.lib.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragmentActivity extends BaseActivity {
    private List<String> mTitleList = Arrays.asList("音频", "视频");
    private List<Fragment> fragments = new ArrayList<>();

    ViewPager main_viewpager;
    private TabLayout mTabLayout;

    @Override
    public void initView() {
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.id_tab_layout);

        Log.e("cwj", "外置SD卡路径 = " + FileUtil.getStoragePath(this));
        Log.e("cwj", "内置SD卡路径 = " + Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.e("cwj", "手机内存根目录路径  = " + Environment.getDataDirectory().getParentFile().getAbsolutePath());
        fragments.add(new AudioFragment());//全部
        fragments.add(new VideoFragment());//本机
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mTitleList, fragments);
        main_viewpager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(main_viewpager);

        //设置默认选中页
        main_viewpager.setCurrentItem(0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileDao.getInstance(this).deleteAll1();
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
