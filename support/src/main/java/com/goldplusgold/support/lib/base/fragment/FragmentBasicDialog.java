package com.goldplusgold.support.lib.base.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by liukai.lk on 2015/4/28.
 */
public class FragmentBasicDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected boolean isActivityAvaiable() {
        Activity activity = getActivity();
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            return !(activity.isFinishing() || activity.isDestroyed());
        } else {
            return !activity.isFinishing();
        }
    }
}
