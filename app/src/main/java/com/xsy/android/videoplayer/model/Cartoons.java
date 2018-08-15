package com.xsy.android.videoplayer.model;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/4.
 */

public class Cartoons {
    private String title;
    ArrayList<Cartoon> cartoons;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Cartoon> getCartoons() {
        return cartoons;
    }

    public void setCartoons(ArrayList<Cartoon> cartoons) {
        this.cartoons = cartoons;
    }
}
