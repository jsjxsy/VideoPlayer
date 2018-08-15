package com.xsy.android.videoplayer.fragment;

import android.os.Bundle;

import com.goldplusgold.support.lib.hybird.WebViewFragment;

/**
 * Created by admin on 2017/9/10.
 */

public class SettingsFragment extends WebViewFragment {

    public static SettingsFragment newInstance(String url,String title) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
