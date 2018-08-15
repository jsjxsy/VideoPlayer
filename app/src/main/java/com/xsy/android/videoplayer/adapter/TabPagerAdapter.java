package com.xsy.android.videoplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.goldplusgold.support.lib.base.adapter.BasePagerAdapter;

/**
 * Created by admin on 2017/8/31.
 */

public class TabPagerAdapter extends BasePagerAdapter<Fragment> {

    private String tabTitles[];

    public TabPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm, mContext);
    }

    public void setTabTitles(@NonNull String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTitles == null)
            return "";
        return tabTitles[position];
    }
}
