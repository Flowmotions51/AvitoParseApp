package com.example.mycompany.avitoparseapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DataItem implements Parcelable {
    private String Id;
    private String url;
    private String title;
    private int price;
    private String time;
    private String phone;
    private String name;
    private String region;
    private String city;
    private String address;
    private String images;
    private String description;
    private int category_Id;
    private int subcategory_Id;
    private int region_Id;
    private int city_Id;
    private Coords coords;
    private List<ItemParam> params;

    public DataItem(String id, String url, String title, int price, String time, String phone, String name, String region, String city, String address, String images, String description, int category_Id, int subcategory_Id, int region_Id, int city_Id, Coords coords, List<ItemParam> params) {
        Id = id;
        this.url = url;
        this.title = title;
        this.price = price;
        this.time = time;
        this.phone = phone;
        this.name = name;
        this.region = region;
        this.city = city;
        this.address = address;
        this.images = images;
        this.description = description;
        this.category_Id = category_Id;
        this.subcategory_Id = subcategory_Id;
        this.region_Id = region_Id;
        this.city_Id = city_Id;
        this.coords = coords;
        this.params = params;
    }

    protected DataItem(Parcel in) {
        Id = in.readString();
        url = in.readString();
        title = in.readString();
        price = in.readInt();
        time = in.readString();
        phone = in.readString();
        name = in.readString();
        region = in.readString();
        city = in.readString();
        address = in.readString();
        images = in.readString();
        description = in.readString();
        category_Id = in.readInt();
        subcategory_Id = in.readInt();
        region_Id = in.readInt();
        city_Id = in.readInt();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public int getSubcategory_Id() {
        return subcategory_Id;
    }

    public void setSubcategory_Id(int subcategory_Id) {
        this.subcategory_Id = subcategory_Id;
    }

    public int getRegion_Id() {
        return region_Id;
    }

    public void setRegion_Id(int region_Id) {
        this.region_Id = region_Id;
    }

    public int getCity_Id() {
        return city_Id;
    }

    public void setCity_Id(int city_Id) {
        this.city_Id = city_Id;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public List<ItemParam> getParams() {
        return params;
    }

    public void setParams(List<ItemParam> params) {
        this.params = params;
    }


    public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeInt(price);
        dest.writeString(time);
        dest.writeString(phone);
        dest.writeString(name);
        dest.writeString(region);
        dest.writeString(city);
        dest.writeString(address);
        dest.writeString(images);
        dest.writeString(description);
        dest.writeInt(category_Id);
        dest.writeInt(subcategory_Id);
        dest.writeInt(region_Id);
        dest.writeInt(city_Id);
    }
}
