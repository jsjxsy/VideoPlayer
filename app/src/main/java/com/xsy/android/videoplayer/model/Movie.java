package com.xsy.android.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/8/30.
 */

public class Movie implements Parcelable {
    private String image;
    private String title;
    private String description;
    private String mark;
    private String type;
    private String url;
    private int duration;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        dest.writeInt(this.duration);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.mark = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.duration = in.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
