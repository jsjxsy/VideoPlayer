package com.goldplusgold.support.lib.base.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.goldplusgold.support.lib.widget.pulltorefresh.PullToRefreshBase;
import com.goldplusgold.support.lib.widget.pulltorefresh.PullToRefreshListView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected MHandler handler = new MHandler(this);
    protected Context context;
    protected LayoutInflater inflater;
    protected boolean refresh = false;
    public abstract void upRereshView();

    public abstract void downRereshView();

    public abstract void setDatas(T list);

    public void setContext(Context context){
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public abstract void requestData();

    public abstract void setPullView(PullToRefreshListView listView);

    public abstract void notifyChange();

    protected void send(){
        handler.sendEmptyMessage(1);
    }

    static class MHandler extends Handler {
        private WeakReference<BaseListAdapter> adatperWeakReference;
        public MHandler(BaseListAdapter adatper){
            this.adatperWeakReference = new WeakReference<BaseListAdapter>(adatper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(adatperWeakReference.get() != null){
                        adatperWeakReference.get().notifyChange();
                    }
                    break;
            }

        }
    }

}
