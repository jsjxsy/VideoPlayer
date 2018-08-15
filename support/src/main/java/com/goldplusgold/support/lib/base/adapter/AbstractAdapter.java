package com.goldplusgold.support.lib.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbstractAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> listData;
    protected int mType;
    protected LayoutInflater layoutInflater;

    public AbstractAdapter(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    public AbstractAdapter(Context context, List<T> listData, int type) {
        this.context = context;
        this.listData = listData;
        this.mType = type;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<T> values) {
        this.listData = values;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> values) {
        this.listData.addAll(values);
        // 更新数据集
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData == null ? null : listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View inflate(int layoutId) {
        return layoutInflater.inflate(layoutId, null);
    }

}