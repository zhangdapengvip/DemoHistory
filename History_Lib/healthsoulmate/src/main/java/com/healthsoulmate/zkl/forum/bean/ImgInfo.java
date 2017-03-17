package com.healthsoulmate.zkl.forum.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by ZeroAries on 2016/5/4.
 * 图片信息
 */
public class ImgInfo implements Parcelable {
    
    private String path;
    private Uri uri;
    private File file;

    public ImgInfo() {
    }

    public ImgInfo(String path, Uri uri, File file) {
        this.path = path;
        this.uri = uri;
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeParcelable(this.uri, flags);
        dest.writeSerializable(this.file);
    }

    protected ImgInfo(Parcel in) {
        this.path = in.readString();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.file = (File) in.readSerializable();
    }

    public static final Creator<ImgInfo> CREATOR = new Creator<ImgInfo>() {
        @Override
        public ImgInfo createFromParcel(Parcel source) {
            return new ImgInfo(source);
        }

        @Override
        public ImgInfo[] newArray(int size) {
            return new ImgInfo[size];
        }
    };
}
