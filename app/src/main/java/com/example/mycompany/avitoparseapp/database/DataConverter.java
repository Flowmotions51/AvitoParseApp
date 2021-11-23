package com.example.mycompany.avitoparseapp.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromPhotosLinkList(List<String> photosLinkList) {
        if (photosLinkList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(photosLinkList, type);
        return json;
    }

    @TypeConverter
    public List<String> toPhotosLinkList(String links) {
        if (links == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> photosLinkList = gson.fromJson(links, type);
        return photosLinkList;
    }
}
