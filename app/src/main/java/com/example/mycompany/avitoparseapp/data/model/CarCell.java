package com.example.mycompany.avitoparseapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "CARCELL")
public class CarCell implements Parcelable {
    private final String previewImageUrl;
    private final String firstImgUrl;
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private final String linkToItem;
    private final String carName;
    private final String price;
    @ColumnInfo(name="id")
    public int id;

    private boolean isFavorite;

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public CarCell(String previewImageUrl, String firstImgUrl, String linkToItem, String carName, String price) {
        this.previewImageUrl = previewImageUrl;
        this.firstImgUrl = firstImgUrl;
        this.linkToItem = linkToItem;
        this.carName = carName;
        this.price = price;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public String getFirstImgUrl() {
        return firstImgUrl;
    }

    public String getLinkToItem() {
        return linkToItem;
    }

    public String getCarName() {
        return carName;
    }

    public String getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

//    public int getCarId() {
//        return carId;
//    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(previewImageUrl);
        dest.writeString(firstImgUrl);
        dest.writeString(linkToItem);
        dest.writeString(carName);
        dest.writeString(price);
        dest.writeInt(id);
//        dest.writeInt(carId);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }


    protected CarCell(Parcel in) {
        previewImageUrl = in.readString();
        firstImgUrl = in.readString();
        linkToItem = in.readString();
        carName = in.readString();
        price = in.readString();
        id = in.readInt();
//        carId = in.readInt();
        isFavorite = in.readByte() != 0;
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
}
