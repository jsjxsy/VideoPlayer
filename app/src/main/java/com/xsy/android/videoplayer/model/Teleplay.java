package com.xsy.android.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/4.
 */

public class Teleplay implements Parcelable {
    private String image;
    private String title;
    private String description;
    private String mark;
    private String type;
    private String url;
    private int episodes;
    private ArrayList<Episode> teleplay;
    private int position;
    private int currentDuration;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public ArrayList<Episode> getTeleplay() {
        return teleplay;
    }

    public void setTeleplay(ArrayList<Episode> teleplay) {
        this.teleplay = teleplay;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(int currentDuration) {
        this.currentDuration = currentDuration;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.mark);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeInt(this.episodes);
        dest.writeList(this.teleplay);
        dest.writeInt(this.position);
        dest.writeInt(this.currentDuration);
    }

    public Teleplay() {
    }

    protected Teleplay(Parcel in) {
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.mark = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.episodes = in.readInt();
        this.teleplay = new ArrayList<Episode>();
        in.readList(this.teleplay, Episode.class.getClassLoader());
        this.position = in.readInt();
        this.currentDuration = in.readInt();
    }

    public static final Parcelable.Creator<Teleplay> CREATOR = new Parcelable.Creator<Teleplay>() {
        @Override
        public Teleplay createFromParcel(Parcel source) {
            return new Teleplay(source);
        }

        @Override
        public Teleplay[] newArray(int size) {
            return new Teleplay[size];
        }
    };
}
