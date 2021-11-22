package com.example.mycompany.avitoparseapp.api;

import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.GetItemsResponse;
import com.example.mycompany.avitoparseapp.data.model.CarItemResponse;
import com.example.mycompany.avitoparseapp.data.model.Model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("getCells/{brand}/{model}")
    Single<GetItemsResponse> getResponseItemList(@Path("brand")String brand, @Path("model")String model);

    @GET("getItem/{link}")
    Single<CarItemResponse> getItem(@Path("link")String link);

    @GET("getBrandList")
    Single<List<Brand>> getBrandList();

    @GET("getModelsList/{brand}")
    Single<List<Model>> getModelsList(@Path("brand")String brand);
}
