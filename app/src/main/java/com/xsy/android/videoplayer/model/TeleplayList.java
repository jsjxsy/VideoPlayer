package com.xsy.android.videoplayer.model;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/4.
 */

public class TeleplayList {
    private String title;
    private ArrayList<Teleplay> teleplays;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Teleplay> getTeleplays() {
        return teleplays;
    }

    public void setTeleplays(ArrayList<Teleplay> teleplays) {
        this.teleplays = teleplays;
    }
}
