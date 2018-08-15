//package com.goldplusgold.support.lib.widget;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//
//import com.alibaba.intl.android.picture.widget.gesture.GestureImageView;
//
//public class GestureViewPager extends ViewPagerNoCrashInPicture {
//
//    private boolean mAcceptEvents;
//
//    public GestureViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        boolean result = super.onInterceptTouchEvent(event) || !mAcceptEvents;
//        if (((event.getAction() == 2) && (event.getPointerCount() == 1)) || (event.getAction() != 2)) {
//            GestureImageView localZoomableImageView = (GestureImageView) findViewWithTag(getCurrentItem());
//            if ((localZoomableImageView != null && !localZoomableImageView.isZoomed())) {
//                return result;
//            }
//        }
//        return false;
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        try {
//            return super.onTouchEvent(ev);
//        } catch (IllegalArgumentException e) {
//            //Fix for support lib bug, happening when onDestroy is
//            return true;
//        }
//    }
//
//    public void setAcceptEvents(boolean mAcceptEvents) {
//        this.mAcceptEvents = mAcceptEvents;
//    }
//
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        mAcceptEvents = true;
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        mAcceptEvents = false;
//    }
//}