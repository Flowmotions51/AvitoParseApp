package com.example.mycompany.avitoparseapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "CARCELL", indices = {@Index(value = "id", unique = true),
        @Index(value = "carId", unique = true)})
public class CarCell implements Parcelable {
    private String previewImageUrl;
    private String firstImgUrl;
    private String linkToItem;
    private String carName;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @ColumnInfo(name="carId")
    public int carId;


    private boolean isFavorite;

    public CarCell(String previewImageUrl, String firstImgUrl, String linkToItem, String carName) {
        this.previewImageUrl = previewImageUrl;
        this.firstImgUrl = firstImgUrl;
        this.linkToItem = linkToItem;
        this.carName = carName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarCell carCell = (CarCell) o;
        return Objects.equals(previewImageUrl, carCell.previewImageUrl) && Objects.equals(firstImgUrl, carCell.firstImgUrl) && Objects.equals(linkToItem, carCell.linkToItem) && Objects.equals(carName, carCell.carName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previewImageUrl, firstImgUrl, linkToItem, carName);
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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
