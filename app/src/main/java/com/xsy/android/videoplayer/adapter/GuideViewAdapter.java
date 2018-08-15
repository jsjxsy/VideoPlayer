package com.xsy.android.videoplayer.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GuideViewAdapter extends PagerAdapter {

    private List<View> mGuideViewList;

    public GuideViewAdapter(List<View> list) {
        this.mGuideViewList = list;
    }

    @Override
    public int getCount() {
        return mGuideViewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mGuideViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mGuideViewList.get(position));
        return mGuideViewList.get(position);
    }

}
