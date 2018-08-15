package com.xsy.android.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/9/10.
 */

public class Episode implements Parcelable {
    private String title;
    private int episode;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.episode);
        dest.writeString(this.url);
    }

    public Episode() {
    }

    protected Episode(Parcel in) {
        this.title = in.readString();
        this.episode = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel source) {
            return new Episode(source);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };
}
