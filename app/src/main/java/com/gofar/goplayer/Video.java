package com.gofar.goplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lcf
 * @date 8/8/2018 上午 11:51
 * @since 1.0
 */
public class Video implements Parcelable{

    private int id;
    private String name;
    private String path;
    private String resolution;
    private String thumbnail;
    private long width;
    private long height;
    private long size;
    private long duration;

    public Video() {
    }

    public Video(int id, String name, String path, String resolution, String thumbnail, long width, long height, long size, long duration) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.resolution = resolution;
        this.thumbnail = thumbnail;
        this.width = width;
        this.height = height;
        this.size = size;
        this.duration = duration;
    }

    protected Video(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        resolution = in.readString();
        thumbnail = in.readString();
        width = in.readLong();
        height = in.readLong();
        size = in.readLong();
        duration = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(resolution);
        dest.writeString(thumbnail);
        dest.writeLong(width);
        dest.writeLong(height);
        dest.writeLong(size);
        dest.writeLong(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
