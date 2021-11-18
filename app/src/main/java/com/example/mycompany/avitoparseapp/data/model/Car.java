package com.example.mycompany.avitoparseapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

public class Car implements Parcelable {
    private String carName;
    private String mainPhotoLink;
    private String telephonePhotoLink;
    private List<String> photoLinks;

    private String carDescription;

    public Car(String carName, String mainPhotoLink, String telephonePhotoLink, List<String> photoLinks,
               String carDescription) {
        this.carName = carName;
        this.mainPhotoLink = mainPhotoLink;
        this.telephonePhotoLink = telephonePhotoLink;
        this.photoLinks = photoLinks;
        this.carDescription = carDescription;
    }

    protected Car(Parcel in) {
        carName = in.readString();
        mainPhotoLink = in.readString();
        telephonePhotoLink = in.readString();
        photoLinks = in.createStringArrayList();
        carDescription = in.readString();
    }

    public String getCarName() {
        return carName;
    }

    public String getMainPhotoLink() {
        return mainPhotoLink;
    }

    public String getTelephonePhotoLink() {
        return telephonePhotoLink;
    }

    public List<String> getPhotoLinks() {
        return photoLinks;
    }


    public String getCarDescription() {
        return carDescription;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carName);
        dest.writeString(mainPhotoLink);
        dest.writeString(telephonePhotoLink);
        dest.writeStringList(photoLinks);
        dest.writeString(carDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carName, car.carName) && Objects.equals(mainPhotoLink, car.mainPhotoLink) && Objects.equals(telephonePhotoLink, car.telephonePhotoLink) && Objects.equals(photoLinks, car.photoLinks) && Objects.equals(carDescription, car.carDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carName, mainPhotoLink, telephonePhotoLink, photoLinks, carDescription);
    }
}
