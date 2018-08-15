package com.goldplusgold.support.lib.widget.recyclerview.listener;

import android.view.View;

/**
 * Created by guangming.chengm on 15/9/22.
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);
    boolean onItemLongClick(View view, int position);
}
