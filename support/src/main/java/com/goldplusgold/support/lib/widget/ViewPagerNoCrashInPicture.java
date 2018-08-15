package com.goldplusgold.support.lib.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sdlu on 15/11/23.
 */
public class ViewPagerNoCrashInPicture extends ViewPager {

    private boolean mAcceptEvents;

    public ViewPagerNoCrashInPicture(Context context) {
        super(context);
    }

    public ViewPagerNoCrashInPicture(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAcceptEvents(boolean mAcceptEvents) {
        this.mAcceptEvents = mAcceptEvents;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) || !mAcceptEvents;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //Fix for support lib bug, happening when onDestroy is
            return true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAcceptEvents = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAcceptEvents = false;
    }
}
