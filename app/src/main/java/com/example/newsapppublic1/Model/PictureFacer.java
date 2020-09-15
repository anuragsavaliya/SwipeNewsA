package com.example.newsapppublic1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureFacer implements Parcelable {
    public static final Creator<PictureFacer> CREATOR = new Creator<PictureFacer>() {
        @Override
        public PictureFacer createFromParcel(Parcel in) {
            return new PictureFacer(in);
        }

        @Override
        public PictureFacer[] newArray(int size) {
            return new PictureFacer[size];
        }
    };
    String thumbPath;
    private String picturName;
    private String picturePath;
    private String pictureSize;
    private String imageUri;
    private Boolean selected = false;

    public PictureFacer(String picturName, String picturePath, String pictureSize, String imageUri) {
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.pictureSize = pictureSize;
        this.imageUri = imageUri;
    }

    public PictureFacer() {

    }

    protected PictureFacer(Parcel in) {
        picturName = in.readString();
        picturePath = in.readString();
        pictureSize = in.readString();
        imageUri = in.readString();
        byte tmpSelected = in.readByte();
        selected = tmpSelected == 0 ? null : tmpSelected == 1;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(picturName);
        parcel.writeString(picturePath);
        parcel.writeString(pictureSize);
        parcel.writeString(imageUri);
        parcel.writeByte((byte) (selected == null ? 0 : selected ? 1 : 2));
    }
}