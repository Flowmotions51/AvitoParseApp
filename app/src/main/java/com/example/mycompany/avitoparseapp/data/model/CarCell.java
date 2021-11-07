package com.example.mycompany.avitoparseapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarCell implements Parcelable {
    private String previewImageUrl;
    private String linkToItem;
    private String carName;

    public CarCell(String previewImageUrl, String linkToItem, String carName) {
        this.previewImageUrl = previewImageUrl;
        this.linkToItem = linkToItem;
        this.carName = carName;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public String getLinkToItem() {
        return linkToItem;
    }

    public String getCarName() {
        return carName;
    }

    protected CarCell(Parcel in) {
        previewImageUrl = in.readString();
        linkToItem = in.readString();
        carName = in.readString();
    }

    public static final Creator<CarCell> CREATOR = new Creator<CarCell>() {
        @Override
        public CarCell createFromParcel(Parcel in) {
            return new CarCell(in);
        }

        @Override
        public CarCell[] newArray(int size) {
            return new CarCell[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(previewImageUrl);
        dest.writeString(linkToItem);
        dest.writeString(carName);
    }
}
