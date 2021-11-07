package com.example.mycompany.avitoparseapp.data.model;

import java.util.List;

public class Car {
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
}
