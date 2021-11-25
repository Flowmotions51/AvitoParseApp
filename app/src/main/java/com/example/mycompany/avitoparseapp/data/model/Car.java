package com.example.mycompany.avitoparseapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Objects;

public class Car implements Parcelable {
    private final String carName;
    private final String linkToItem;
    private final List<String> photoLinks;

    private final String phone;
    private final String carDescription;

    public Car(String carName, String linkToItem, List<String> photoLinks,
               String carDescription, String phone) {
        this.carName = carName;
        this.linkToItem = linkToItem;
        this.photoLinks = photoLinks;
        this.carDescription = carDescription;
        this.phone = phone;
    }

    public String getCarName() {
        return carName;
    }

    public String getLinkToItem() {
        return linkToItem;
    }

    public List<String> getPhotoLinks() {
        return photoLinks;
    }

    public String getPhone() {
        return phone;
    }

    public String getCarDescription() {
        return carDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carName);
        dest.writeString(linkToItem);
        dest.writeStringList(photoLinks);
        dest.writeString(phone);
        dest.writeString(carDescription);
    }

    protected Car(Parcel in) {
        carName = in.readString();
        linkToItem = in.readString();
        photoLinks = in.createStringArrayList();
        phone = in.readString();
        carDescription = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carName, car.carName) && Objects.equals(linkToItem, car.linkToItem) && Objects.equals(photoLinks, car.photoLinks) && Objects.equals(phone, car.phone) && Objects.equals(carDescription, car.carDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carName, linkToItem, photoLinks, phone, carDescription);
    }
}
